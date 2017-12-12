package com.bookshelf.activity.authorized.payment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bookshelf.R;

public class ResultFailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_failed);

        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        TextView textView = (TextView) findViewById(R.id.result_text);
        textView.setText(extras.getString("status"));
    }

    public void onBackButton(View view) {
        super.onBackPressed();
    }
}
