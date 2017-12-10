package com.bookshelf.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.api.Authentication;
import com.bookshelf.api.CountryService;
import com.bookshelf.data.Address;
import com.bookshelf.data.CsrfToken;
import com.bookshelf.data.User;
import com.bookshelf.data.collection.Countries;

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

    private User mUser;
    private Address mAddress;

    private Countries mCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_address);

        ButterKnife.bind(this);

        mUser = getUserFromBundle();

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                    authenticateToPlainUser();

                }
            }
        });
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

        // ...

        return true;
    }

    private void authenticateToPlainUser(){
        Authentication auth = generateCallService(Authentication.class);
        Call<CsrfToken> call = auth.perform("plain@gmail.com", "password");
        call.enqueue(new AuthenticationCallback());
    }

    private void getCountries(){
        CountryService c = generateCallService(CountryService.class);
        Call<Countries> call = c.getAllCountries();
        call.enqueue(new CountriesCallback());
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

                //toHome();
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

            if(response.isSuccessful()){
                Toast.makeText(SignUpAddressActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(SignUpAddressActivity.this, "asdf", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Countries> call, Throwable t) {
            super.onFailure(call, t);

            Toast.makeText(SignUpAddressActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

}
