package com.bookshelf.application;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.bookshelf.data.CsrfToken;

/**
 * Created by Krzysztof on 06.12.2017.
 */

public class ShopApplication extends Application {

    private CsrfToken csrfToken;
    private String cookies = "";

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Bundle bundle = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData;


        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
