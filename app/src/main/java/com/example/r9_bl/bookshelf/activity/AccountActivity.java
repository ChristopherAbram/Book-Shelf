package com.example.r9_bl.bookshelf.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.r9_bl.bookshelf.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
