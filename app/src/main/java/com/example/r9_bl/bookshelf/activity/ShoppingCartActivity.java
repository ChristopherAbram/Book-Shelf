package com.example.r9_bl.bookshelf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.r9_bl.bookshelf.R;

import java.util.ArrayList;

import list.ShoppingCartAdapter;

public class ShoppingCartActivity extends AppCompatActivity {

    ListView listView;
    Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        listView = findViewById(R.id.list_view);
        payButton = findViewById(R.id.pay_button);

        //TODO: itemIDs/list should obtain from database.
        String[] itemIDs = new String[]{"11111111", "22222222", "33333333"};
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < itemIDs.length; ++i) {
            list.add(itemIDs[i]);
        }

        if (list.isEmpty()) {
            payButton.setVisibility(View.GONE);
        }else {
            ShoppingCartAdapter adapter = new ShoppingCartAdapter(this, list);
            listView.setAdapter(adapter);
        }

    }

}
