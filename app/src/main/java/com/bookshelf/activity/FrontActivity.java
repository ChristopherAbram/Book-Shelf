package com.bookshelf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bookshelf.R;
import com.bookshelf.activity.authorized.HomeActivity;
import com.bookshelf.application.ShopApplication;

public class FrontActivity extends Activity {

    private final String TAG = getClass().getName();
    private boolean auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);


    }

    @Override
    protected void onResume(){
        super.onResume();

        auth = ((ShopApplication) getApplication()).isAuthorized();
        Log.d(TAG, "Authenticated: " + (auth ? "YES" : "NO"));
        if(auth)
            toHome();
    }

    public void toSignIn(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void toSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void toHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}