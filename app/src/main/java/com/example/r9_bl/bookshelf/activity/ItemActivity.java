package com.example.r9_bl.bookshelf.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.r9_bl.bookshelf.R;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("itemID");

        TextView nameTextView = findViewById(R.id.name_text_view);
        nameTextView.setText(value);
    }
}
