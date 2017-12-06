package com.bookshelf.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bookshelf.activity.BaseActivity;
import com.bookshelf.api.Authentication;
import com.bookshelf.application.Constants;
import com.bookshelf.application.ShopApplication;
import com.bookshelf.data.CsrfToken;
import com.bookshelf.data.Role;
import com.bookshelf.security.UnsafeOkHttpClient;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Krzysztof on 04.12.2017.
 */

public class AuthenticationService extends IntentService {

    private final String TAG = getClass().getName();
    private Retrofit mRetrofit;

    public AuthenticationService(){
        super("AuthenticationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while(true){
            try {
                CsrfToken token = ((ShopApplication) getApplication()).getCsrfToken();
                String csrf = token != null ? token.getToken() : "";

                // Get session id:
                String cookies = ((ShopApplication) getApplication()).getCookies();
                UnsafeOkHttpClient.setCookies(cookies);
                UnsafeOkHttpClient.setCsrfToken(csrf);
                OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(Constants.API_SERVER_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create());

                mRetrofit = builder.build();

                Authentication auth = mRetrofit.create(Authentication.class);
                Call<Role> call = auth.check("1");

                Response<Role> result = call.execute();
                if(result.isSuccessful())
                    Log.d(TAG, "Authenticated");
                else
                    Log.d(TAG, "Not Authenticated");

                cookies = BaseActivity.getCookiesFromHeaders(result);
                Log.d(TAG, cookies);
                if(!cookies.equals(""))
                    ((ShopApplication) getApplication()).setCookies(cookies);

                Thread.sleep(Constants.AUTHENTICATION_CHECKUP_PERIOD * 1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
