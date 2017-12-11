package com.bookshelf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.activity.authorized.SignOutActivity;
import com.bookshelf.api.AddressService;
import com.bookshelf.api.Authentication;
import com.bookshelf.api.CountryService;
import com.bookshelf.api.UserService;
import com.bookshelf.application.Constants;
import com.bookshelf.data.Address;
import com.bookshelf.data.Country;
import com.bookshelf.data.CsrfToken;
import com.bookshelf.data.User;
import com.bookshelf.data.collection.Countries;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class SignUpAddressActivity extends BaseActivity {

    private final String TAG = getClass().getName();

    @BindView(R.id.country)
    Spinner country;

    @BindView(R.id.city)
    EditText city;

    @BindView(R.id.zip)
    EditText zip;

    @BindView(R.id.street)
    EditText street;

    @BindView(R.id.house)
    EditText house;

    @BindView(R.id.flat)
    EditText flat;

    @BindView(R.id.sign_in)
    Button signIn;

    private final int LOG_OUT_CODE = 1;

    private User mUser;
    private Address mAddress;

    private Countries mCountries;
    private Country mSelectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setBaseUrl(Constants.AUTH_SERVER_URL);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_address);

        ButterKnife.bind(this);

        mUser = getUserFromBundle();

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mSelectedCountry = mCountries.getCountries().get(position);
                    Toast.makeText(SignUpAddressActivity.this, "Selected: " + mSelectedCountry.getName(), Toast.LENGTH_LONG).show();
                } catch(NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    showProgressBar();
                    postUser();
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        showProgressBar();
        authenticateToPlainUser();
    }

    private User getUserFromBundle(){
        return (User) getIntent().getParcelableExtra("user");
    }

    private Address getAddressFromView(){
        Address address = new Address();

        address.setCity(city.getText().toString());
        address.setZip(setString(zip.getText().toString()));
        address.setStreet(setString(street.getText().toString()));
        address.setHouse(setString(house.getText().toString()));
        address.setFlat(setString(flat.getText().toString()));

        return address;
    }

    private String setString(String str){
        return str.equals("") ? null : str;
    }

    private boolean isValid(){
        mAddress = getAddressFromView();

        try {
            if (mAddress.getCity().equals("") || mAddress.getZip().equals("") || mAddress.getStreet().equals("")) {
                Toast.makeText(SignUpAddressActivity.this, "City, Zip and Street can't be empty.", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch(NullPointerException e){
            Toast.makeText(SignUpAddressActivity.this, "City, Zip and Street can't be empty.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void authenticateToPlainUser(){
        Authentication auth = generateCallService(Authentication.class);
        Call<CsrfToken> call = auth.perform("plain@gmail.com", "password");
        call.enqueue(new AuthenticationCallback());
    }

    private void getCountries(){
        setBaseUrl(Constants.API_SERVER_URL);
        rebuildRetrofitInstance();
        CountryService c = generateCallService(CountryService.class);
        Call<Countries> call = c.getAllCountries();
        call.enqueue(new CountriesCallback());
    }

    private void postUser(){
        mUser.setRoleId(3);
        UserService c = generateCallService(UserService.class);
        Call<String> call = c.addUser(mUser);
        call.enqueue(new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                super.onResponse(call, response);
                if(response.isSuccessful()){
                    Log.d(TAG, "Last user id: " + response.body());
                    if(response.body()!= null && !response.body().equals("null")) {
                        Integer userId = Integer.parseInt(response.body());
                        Log.d(TAG, "Last user id: " + userId);
                        postAddress(userId);
                    }
                    else {
                        Toast.makeText(SignUpAddressActivity.this, "Problem with adding new account.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(SignUpAddressActivity.this, "Problem with adding new account.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(SignUpAddressActivity.this, "Problem with adding new account.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postAddress(Integer userId){
        mAddress.setUserId(userId);
        if(mSelectedCountry != null)
            mAddress.setCountryId(mSelectedCountry.getId());
        AddressService c = generateCallService(AddressService.class);
        Call<String> call = c.addAddress(mAddress);
        call.enqueue(new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                super.onResponse(call, response);

                if(response.isSuccessful()){
                    Log.d(TAG, "LastAddressId: " + response.body());
                    logoutPlain();
                }
                else {
                    Toast.makeText(SignUpAddressActivity.this, "Account added but adding address failed.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(SignUpAddressActivity.this, "Account added but adding address failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void logoutPlain(){
        Intent intent = new Intent(this, SignOutActivity.class);
        startActivityForResult(intent, LOG_OUT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOG_OUT_CODE) {
            if(resultCode == Activity.RESULT_OK){
                toFront();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                hideProgressBar();
                if(data.getExtras().containsKey("result"))
                    Toast.makeText(getBaseContext(), data.getExtras().get("result").toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void toFront(){
        Intent intent = new Intent(this, FrontActivity.class);
        startActivity(intent);
        finish();
    }

    private class AuthenticationCallback extends Callback<CsrfToken> {
        @Override
        public void onResponse(Call<CsrfToken> call, Response<CsrfToken> response) {
            super.onResponse(call, response); // do not forget to call it!

            if(response.isSuccessful()){
                CsrfToken csrf = response.body();

                getShopApplication().setCsrfToken(csrf);
                getShopApplication().setAuthorized(true);

                Log.d(TAG, csrf.getToken());

                getCountries();
            }
            else {
                // TODO: Inform user that has specified wrong credentials:
                Toast.makeText(SignUpAddressActivity.this, "User does not exist, wrong email or password", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<CsrfToken> call, Throwable t) {
            Toast.makeText(SignUpAddressActivity.this, "Authentication failed, try again later", Toast.LENGTH_LONG).show();
        }
    }

    private class CountriesCallback extends Callback<Countries> {
        @Override
        public void onResponse(Call<Countries> call, Response<Countries> response) {
            super.onResponse(call, response);
            hideProgressBar();
            if(response.isSuccessful()){
                mCountries = response.body();
                ArrayList<String> countryNames = new ArrayList<>();
                for(Country country : mCountries.getCountries())
                    countryNames.add(country.getName());

                // TODO: adapter can be improved..
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignUpAddressActivity.this, android.R.layout.simple_spinner_item, countryNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                SignUpAddressActivity.this.country.setAdapter(adapter);
            }
            else {
                Toast.makeText(SignUpAddressActivity.this, "Unable to fetch countries.", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Countries> call, Throwable t) {
            super.onFailure(call, t);
            hideProgressBar();
            Toast.makeText(SignUpAddressActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

}
