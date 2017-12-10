package com.bookshelf.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bookshelf.database.Database;

/**
 * Created by Maksim on 12/6/2017.
 */

public class SearchActivity extends AppCompatActivity {

    String query;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        query = getIntent().getStringExtra(SearchManager.QUERY);
        Intent intent = new Intent(this, ItemsActivity.class);
        intent.putExtra("searchKeyword", query);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        query = intent.getStringExtra(SearchManager.QUERY);
        Intent newIntent = new Intent(this, ItemsActivity.class);
        newIntent.putExtra("searchKeyword", query);
        startActivity(newIntent);
        finish();
    }

}
