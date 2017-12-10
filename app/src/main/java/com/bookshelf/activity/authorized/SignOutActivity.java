package com.bookshelf.activity.authorized;

import android.os.Bundle;

import com.bookshelf.R;

public class SignOutActivity extends AuthorizedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);


    }

    @Override
    protected void onResume(){
        super.onResume();
        
    }
}
