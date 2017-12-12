package com.bookshelf.application;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.bookshelf.data.CsrfToken;
import com.simplify.android.sdk.Simplify;

/**
 * Created by Krzysztof on 06.12.2017.
 */

public class ShopApplication extends Application {

    private CsrfToken csrfToken;
    private String cookies = "";

    private boolean mAuthorized;

    Simplify simplify;

    public static final String API_KEY = "com.bookshelf.payment.apiKey";
    public static final String ANDROID_PAY_PUBLIC_KEY = "com.bookshelf.payment.androidPayPublicKey";

    @Override
    public void onCreate() {
        super.onCreate();
        simplify = new Simplify();

        try {
            Bundle bundle = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData;

            // init simplify api key
            String apiKey = bundle.getString(API_KEY, null);
            if (apiKey != null)
                simplify.setApiKey(apiKey);

            // init android pay public key
            String androidPayPublicKey = bundle.getString(ANDROID_PAY_PUBLIC_KEY, null);
            if (androidPayPublicKey != null)
                simplify.setAndroidPayPublicKey(androidPayPublicKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAuthorized() {
        return mAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        this.mAuthorized = authorized;
    }

    public CsrfToken getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(CsrfToken token) {
        this.csrfToken = token;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public Simplify getSimplify() {
        return simplify;
    }
}
