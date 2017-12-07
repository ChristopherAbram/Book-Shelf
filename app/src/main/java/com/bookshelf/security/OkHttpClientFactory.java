package com.bookshelf.security;

import okhttp3.OkHttpClient;

/**
 * Created by Krzysztof on 07.12.2017.
 */

public class OkHttpClientFactory {

    public static OkHttpClient getUnsafeOkHttpClient(String csrfToken, String cookies){
        UnsafeOkHttpClient.setCsrfToken(csrfToken);
        UnsafeOkHttpClient.setCookies(cookies);
        return UnsafeOkHttpClient.getUnsafeOkHttpClient();
    }

    public static OkHttpClient getTrustedOkHttpClient(String csrfToken, String cookies){
        CustomTrust trust = new CustomTrust(csrfToken, cookies);
        return trust.create();
    }
}
