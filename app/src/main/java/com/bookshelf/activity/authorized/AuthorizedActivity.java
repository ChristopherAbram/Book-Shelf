package com.bookshelf.activity.authorized;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bookshelf.activity.BaseActivity;
import com.bookshelf.activity.FrontActivity;

abstract public class AuthorizedActivity extends BaseActivity {

    private final String TAG = getClass().getName();

    private boolean auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume(){
        super.onResume();

        auth = getShopApplication().isAuthorized();
        Log.d(TAG, "Authenticated: " + (auth ? "YES" : "NO"));
        if(!auth)
            toFront();
    }

    private void toFront(){
        Intent intent = new Intent(this, FrontActivity.class);
        startActivity(intent);
    }
}
