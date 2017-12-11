package com.bookshelf.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bookshelf.application.Constants;
import com.bookshelf.application.ShopApplication;
import com.bookshelf.data.CsrfToken;
import com.bookshelf.security.OkHttpClientFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


abstract public class BaseActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();

    private Retrofit mRetrofit;
    private URL mURL;

    protected CsrfToken csrfToken;
    protected String cookies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(mURL == null)
            setBaseUrl(Constants.API_SERVER_URL);

        // Get Csrf token, could be null:
        csrfToken = getShopApplication().getCsrfToken();
        // Get session id:
        cookies = getShopApplication().getCookies();

        buildRetrofitInstance();
    }

    protected void setBaseUrl(String url){
        try {
            mURL = new URL(url);
        } catch(MalformedURLException e){}
    }

    protected URL getBaseUrl(){
        return mURL;
    }

    protected <T> T generateCallService(Class<T> service){
        return mRetrofit.create(service);
    }

    private OkHttpClient createOkHttpClient(){
        // Get Csrf token, could be null:
        csrfToken = getShopApplication().getCsrfToken();
        // Get session id:
        cookies = getShopApplication().getCookies();

        Log.d(TAG, "Cookies: "  + cookies);
        Log.d(TAG, "Csrf: "  + (csrfToken != null ? csrfToken.getToken() : ""));

        return OkHttpClientFactory.getTrustedOkHttpClient(
                (csrfToken != null ? csrfToken.getToken() : ""),
                cookies);
    }

    private void buildRetrofitInstance(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(mURL.toString())
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create());

        mRetrofit = builder.build();
    }

    protected void rebuildRetrofitInstance(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(mURL.toString())
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create());

        mRetrofit = builder.build();
    }

    public class Callback<T> implements retrofit2.Callback<T> {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            String cookies = getCookiesFromHeaders(response);
            BaseActivity.this.cookies = cookies;

            Log.d("Cookies: ", cookies);
            if(!cookies.equals(""))
                getShopApplication().setCookies(cookies);
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {

        }
    }

    protected void showProgressBar(){
        // TODO: Show loading...
    }

    protected void hideProgressBar(){
        // TODO: Hide loading...
    }

    public ShopApplication getShopApplication(){
        return (ShopApplication) getApplication();
    }

    public static <T> String getCookiesFromHeaders(Response<T> response){
        String SessId = "";
        String XSRF = "";

        try {
            List<String> headers = response.headers().values("set-cookie");
            for (String he : headers) {
                if (he.contains("PHPSESSID"))
                    SessId = he.substring(10, 42);

                if (he.contains("XSRF-TOKEN"))
                    XSRF = he.substring(11, 31);
            }

            String cookies = "";
            if (!SessId.equals(""))
                cookies = "PHPSESSID=" + SessId;

            if (!SessId.equals("") && !XSRF.equals(""))
                cookies += "; XSRF-TOKEN=" + XSRF;
            else if (SessId.equals("") && !XSRF.equals(""))
                cookies = "XSRF-TOKEN=" + XSRF;

            return cookies;
        } catch(NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

}
