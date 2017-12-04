package service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import api.Authentication;
import data.CsrfToken;
import data.User;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import security.UnsafeOkHttpClient;

/**
 * Created by Krzysztof on 04.12.2017.
 */

public class AuthenticationService extends IntentService {

    private final String TAG = getClass().getName();

    public AuthenticationService(){
        super("AuthenticationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Bundle extras = intent.getExtras();
        User user = (User) extras.get("user");

        Log.d(TAG, user.getEmail() + ", " + user.getPassword());

        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://bookshelf.krzysztofabram.pl")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        Authentication auth = retrofit.create(Authentication.class);
        Call<CsrfToken> call = auth.perform(user.getEmail(), user.getPassword());

        try {
            Response<CsrfToken> result = call.execute();
            Log.d(TAG, "Code: " + result.body().getToken());
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
