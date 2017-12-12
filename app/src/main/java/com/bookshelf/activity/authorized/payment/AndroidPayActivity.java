package com.bookshelf.activity.authorized.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.application.Constants;
import com.bookshelf.application.ShopApplication;
import com.bookshelf.data.Item;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.LineItem;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.simplify.android.sdk.CardToken;
import com.simplify.android.sdk.Simplify;

public class AndroidPayActivity extends AppCompatActivity implements Simplify.AndroidPayCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    static final String TAG = AndroidPayActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;
    private MaskedWallet mMaskedWallet;
    private Button mPayButton;
    private Simplify simplify;
    private ProgressBar mProgressBar;

    private Item mShopItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_pay);

        mMaskedWallet = getIntent().getParcelableExtra(WalletConstants.EXTRA_MASKED_WALLET);

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
        initView();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // let the Simplify SDK marshall out the android pay activity results
        if (simplify.handleAndroidPayResult(requestCode, resultCode, data, this)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
    public void onReceivedMaskedWallet(MaskedWallet maskedWallet) {

    }

    @Override
    public void onReceivedFullWallet(FullWallet fullWallet) {
        mProgressBar.setVisibility(View.VISIBLE);

        // create simplify token with wallet
        simplify.createAndroidPayCardToken(fullWallet, new CardToken.Callback() {
            @Override
            public void onSuccess(CardToken cardToken) {

                AsyncHttpClient mClient = new AsyncHttpClient();

                final RequestParams params = new RequestParams();
                params.put("simplifyToken", cardToken.getId());
                params.put("amount", mShopItem.convertToWalletPrice());
                params.put("currency", Constants.DEFAULT_CURRENCY.getCode());
                params.put("description", mShopItem.getName());
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

                        Toast.makeText(AndroidPayActivity.this, "Failure: " + responseString, Toast.LENGTH_LONG).show();
                        // TODO: Display failure screen..
                    }

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                        mProgressBar.setVisibility(View.GONE);
                        // Enable payment button:
                        mPayButton.setEnabled(true);

                        Toast.makeText(AndroidPayActivity.this, "Success: " + responseString, Toast.LENGTH_LONG).show();
                        // TODO: Display success screen..
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();

                mProgressBar.setVisibility(View.GONE);
                mPayButton.setEnabled(true);

                Toast.makeText(AndroidPayActivity.this, throwable.toString(), Toast.LENGTH_LONG).show();
                // TODO: Display failure screen..
            }
        });
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

    private void initView(){
        // Get shop item:
        mShopItem = getItem();

        if(mShopItem != null) {
            // Pay button init:
            mPayButton = (Button) findViewById(R.id.pay_button);
            mPayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmPurchase();
                }
            });

            // Progress bar init:
            mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

            initWalletFragment();
        }
    }

    private void initWalletFragment(){
        WalletFragmentStyle walletFragmentStyle = new WalletFragmentStyle()
            .setMaskedWalletDetailsBackgroundColor(
                ContextCompat.getColor(this, android.R.color.white))
            .setMaskedWalletDetailsButtonBackgroundResource(
                android.R.color.holo_orange_dark);

        WalletFragmentOptions walletFragmentOptions = WalletFragmentOptions.newBuilder()
            .setEnvironment(Constants.WALLET_ENVIRONMENT)
            .setFragmentStyle(walletFragmentStyle)
            .setTheme(WalletConstants.THEME_LIGHT)
            .setMode(WalletFragmentMode.SELECTION_DETAILS)
            .build();

        SupportWalletFragment walletFragment = SupportWalletFragment.newInstance(walletFragmentOptions);

        WalletFragmentInitParams startParams = WalletFragmentInitParams.newBuilder()
            .setMaskedWallet(mMaskedWallet)
            .setMaskedWalletRequestCode(Simplify.REQUEST_CODE_MASKED_WALLET)
            .build();

        walletFragment.initialize(startParams);

        // add Wallet fragment to the UI
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.confirm_wallet_holder, walletFragment)
            .commit();
    }

    private void confirmPurchase() {
        mPayButton.setEnabled(false);

        if (mMaskedWallet == null) {
            Toast.makeText(this, "No masked wallet, can't confirm", Toast.LENGTH_SHORT).show();
            return;
        }
        Wallet.Payments.loadFullWallet(mGoogleApiClient, getFullWalletRequest(), Simplify.REQUEST_CODE_FULL_WALLET);
    }

    FullWalletRequest getFullWalletRequest() {

        return FullWalletRequest.newBuilder()
            .setGoogleTransactionId(mMaskedWallet.getGoogleTransactionId())
            .setCart(Cart.newBuilder()
                .setCurrencyCode(Constants.DEFAULT_CURRENCY.getCode())
                .setTotalPrice("2.00")
                .addLineItem(LineItem.newBuilder()
                    .setCurrencyCode(Constants.DEFAULT_CURRENCY.getCode())
                    .setDescription(mShopItem.getName())
                    .setQuantity(Integer.toString(mShopItem.getAmount()))
                    .setUnitPrice("2.00")
                    .setTotalPrice("2.00")
                    .build())
                .build())
            .build();
    }
}
