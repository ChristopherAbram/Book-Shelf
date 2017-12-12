package com.bookshelf.activity.authorized.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.application.Constants;
import com.bookshelf.application.ShopApplication;
import com.bookshelf.data.Item;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.LineItem;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentMethodTokenizationType;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.WalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.simplify.android.sdk.Card;
import com.simplify.android.sdk.CardEditor;
import com.simplify.android.sdk.CardToken;
import com.simplify.android.sdk.Customer;
import com.simplify.android.sdk.Simplify;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CardFormActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, Simplify.AndroidPayCallback {

    static final String TAG = CardFormActivity.class.getSimpleName();
    static final String WALLET_FRAGMENT_ID = "wallet_fragment";

    private GoogleApiClient mGoogleApiClient;
    private CardEditor mCardEditor;
    private Button mPayButton;
    private Simplify simplify;
    private ProgressBar mProgressBar;

    private Item mShopItem;
    private EditText mCustomerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_form);

        // Initialize simplify instance:
        simplify = ((ShopApplication) getApplication()).getSimplify();

        // Build Google API instance:
        // API: Wallet
        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(Wallet.API, new Wallet.WalletOptions.Builder()
                .setEnvironment(Constants.WALLET_ENVIRONMENT)
                .setTheme(WalletConstants.THEME_LIGHT)
                .build())
            .build();

        // Initialize view:
        init_view();

        // Wallet payment request:
        readyToPayRequest();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null && !mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // let the Simplify SDK marshall out the android pay activity results
        if (simplify.handleAndroidPayResult(requestCode, resultCode, data, this))
            return;

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onReceivedMaskedWallet(MaskedWallet maskedWallet) {
        // launch confirmation activity
        Intent intent = new Intent(getApplicationContext(), AndroidPayActivity.class);
        intent.putExtra(WalletConstants.EXTRA_MASKED_WALLET, maskedWallet);
        intent.putExtra("itemId", mShopItem.getId());
        startActivity(intent);
    }

    @Override
    public void onReceivedFullWallet(FullWallet fullWallet) {

    }

    @Override
    public void onAndroidPayCancelled() {

    }

    @Override
    public void onAndroidPayError(int i) {

    }

    private Item getItem(){
        Bundle extras = getIntent().getExtras();
        Item item = (Item) extras.getParcelable("item");
        return item;
    }

    private void init_view(){
        // Get shop item:
        mShopItem = getItem();

        // Customer name init:
        mCustomerName = (EditText) findViewById(R.id.full_name);

        // Pay button init:
        mPayButton = (Button) findViewById(R.id.pay_button);
        mPayButton.setText(mPayButton.getText().toString() + " " + mShopItem.getPrice() + " " + Constants.DEFAULT_CURRENCY.getCode());
        mPayButton.setEnabled(false);
        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCardToken();
            }
        });

        // Progress bar init:
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // CardEditor init:
        mCardEditor = (CardEditor) findViewById(R.id.card_editor);
        mCardEditor.addOnStateChangedListener(new CardEditor.OnStateChangedListener() {
            @Override
            public void onStateChange(CardEditor cardEditor) {
                mPayButton.setEnabled(cardEditor.isValid());
            }
        });
    }

    void requestCardToken() {

        mProgressBar.setVisibility(View.VISIBLE);
        mPayButton.setEnabled(false);

        final Card card = mCardEditor.getCard();

        Customer customer = new Customer();
        customer.setName(mCustomerName.getText().toString()); // should be validated..
        //customer.setEmail("krzysztofabram1@gmail.com");

        card.setCustomer(customer);

        simplify.createCardToken(card, new CardToken.Callback() {
            @Override
            public void onSuccess(CardToken cardToken) {

                AsyncHttpClient mClient = new AsyncHttpClient();

                final RequestParams params = new RequestParams();
                params.put("simplifyToken", cardToken.getId());
                params.put("amount", mShopItem.convertToWalletPrice());
                params.put("currency", Constants.DEFAULT_CURRENCY.getCode());
                params.put("description", mShopItem.getName());
                params.put("cardName", card.getCustomer().getName());
                params.put("addressCountry", "FI");
                //params.put("customer", "e7RGzkoGX");
                //params.put("customerName", card.getCustomer().getName());
                //params.put("customerEmail", card.getCustomer().getEmail());


                final TextView output = (TextView) findViewById(R.id.output);

                mClient.post(Constants.SERVER + "/" + Constants.CLIENT_TOKEN, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                        mProgressBar.setVisibility(View.GONE);
                        // Enable payment button:
                        mPayButton.setEnabled(true);

                        //Send intent to Result Activity (Failed)
                        Intent resultFailedIntent = new Intent(getBaseContext(), ResultFailedActivity.class);
                        startActivity(resultFailedIntent);
                    }

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                        mProgressBar.setVisibility(View.GONE);
                        // Enable payment button:
                        mPayButton.setEnabled(true);

                        String status = "ERROR";
                        String refId = "0";
                        try {
                            JSONObject response = new JSONObject(responseString);
                            status = response.getString("status");
                            refId = response.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(status.equals("APPROVED")){
                            //Send intent to Result Activity (Success)
                            Intent resultIntent = new Intent(getBaseContext(), ResultActivity.class);
                            resultIntent.putExtra("customerName", card.getCustomer().getName());
                            resultIntent.putExtra("time", new SimpleDateFormat("yyyy MMM dd - HH:mm:ss").format(new Date()));
                            resultIntent.putExtra("item", mShopItem);
                            resultIntent.putExtra("refId", refId);
                            startActivity(resultIntent);
                            finish();
                        } else {
                            //Send intent to Result Activity (Failed)
                            Intent resultFailedIntent = new Intent(getBaseContext(), ResultFailedActivity.class);
                            resultFailedIntent.putExtra("status", status);
                            startActivity(resultFailedIntent);
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {
                //throwable.printStackTrace();
                mProgressBar.setVisibility(View.GONE);
                mPayButton.setEnabled(true);

                //Send intent to Result Activity (Failed)
                Intent resultFailedIntent = new Intent(getBaseContext(), ResultFailedActivity.class);
                startActivity(resultFailedIntent);
            }
        });
    }

    private void readyToPayRequest(){
        IsReadyToPayRequest req = IsReadyToPayRequest.newBuilder()
            .addAllowedCardNetwork(WalletConstants.CardNetwork.MASTERCARD)
            .addAllowedCardNetwork(WalletConstants.CardNetwork.VISA)
            .addAllowedCardNetwork(WalletConstants.CardNetwork.AMEX)
            .addAllowedCardNetwork(WalletConstants.CardNetwork.DISCOVER)
            .build();

        Wallet.Payments.isReadyToPay(mGoogleApiClient, req)
            .setResultCallback(new ResultCallback<BooleanResult>() {
                @Override
                public void onResult(@NonNull BooleanResult booleanResult) {
                    if (booleanResult.getStatus().isSuccess()) {
                        if (booleanResult.getValue()) {
                            //Log.i(TAG, "Android Pay is ready");
                            Toast.makeText(getBaseContext(), "Android Pay is ready", Toast.LENGTH_LONG).show();
                            showAndroidPayButton();
                            return;
                        }
                    }

                    //Log.i(TAG, "Android Pay not ready");
                    Toast.makeText(getBaseContext(), "Android Pay not ready", Toast.LENGTH_LONG).show();
                    hideAndroidPayButton();
                }
            });
    }

    private void showAndroidPayButton() {
        findViewById(R.id.buy_button_layout).setVisibility(View.VISIBLE);

        // Define fragment style
        WalletFragmentStyle fragmentStyle = new WalletFragmentStyle()
                .setBuyButtonText(WalletFragmentStyle.BuyButtonText.BUY_WITH)
                .setBuyButtonAppearance(WalletFragmentStyle.BuyButtonAppearance.ANDROID_PAY_DARK)
                .setBuyButtonWidth(WalletFragmentStyle.Dimension.MATCH_PARENT);

        // Define fragment options
        WalletFragmentOptions fragmentOptions = WalletFragmentOptions.newBuilder()
                .setEnvironment(Constants.WALLET_ENVIRONMENT)
                .setFragmentStyle(fragmentStyle)
                .setTheme(WalletConstants.THEME_LIGHT)
                .setMode(WalletFragmentMode.BUY_BUTTON)
                .build();

        // Create a new instance of WalletFragment
        WalletFragment walletFragment = WalletFragment.newInstance(fragmentOptions);

        // Initialize the fragment with start params
        // Note: If using the provided helper method Simplify.handleAndroidPayResult(int, int, Intent),
        //       you MUST set the request code to Simplify.REQUEST_CODE_MASKED_WALLET
        WalletFragmentInitParams startParams = WalletFragmentInitParams.newBuilder()
                .setMaskedWalletRequest(getMaskedWalletRequest())
                .setMaskedWalletRequestCode(Simplify.REQUEST_CODE_MASKED_WALLET)
                .build();

        walletFragment.initialize(startParams);

        // Add Wallet fragment to the UI
        getFragmentManager().beginTransaction()
                .replace(R.id.buy_button_holder, walletFragment, WALLET_FRAGMENT_ID)
                .commit();

    }

    private void hideAndroidPayButton() {
        findViewById(R.id.buy_button_layout).setVisibility(View.GONE);
    }

    private MaskedWalletRequest getMaskedWalletRequest() {

        PaymentMethodTokenizationParameters parameters =
            PaymentMethodTokenizationParameters.newBuilder()
                .setPaymentMethodTokenizationType(PaymentMethodTokenizationType.NETWORK_TOKEN)
                .addParameter("publicKey", simplify.getAndroidPayPublicKey())
                .build();

        Cart cart = Cart.newBuilder()
            .setCurrencyCode(Constants.DEFAULT_CURRENCY.getCode())
            .setTotalPrice(mShopItem.priceToString())
            .addLineItem(LineItem.newBuilder()
                .setCurrencyCode(Constants.DEFAULT_CURRENCY.getCode())
                .setDescription(mShopItem.getName())
                .setQuantity("1")
                .setUnitPrice("2.00")
                .setTotalPrice("2.00")
                .build())
            .build();

        return MaskedWalletRequest.newBuilder()
            .setMerchantName("BookShelf")
            .setPhoneNumberRequired(true)
            .setShippingAddressRequired(true)
            .setCurrencyCode(Constants.DEFAULT_CURRENCY.getCode())
            .setCart(cart)
            .setEstimatedTotalPrice(Item.priceToString(mShopItem.getTotalPrice()))
            .setPaymentMethodTokenizationParameters(parameters)
            .build();
    }
}
