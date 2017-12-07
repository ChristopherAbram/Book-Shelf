package com.example.r9_bl.bookshelf.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.r9_bl.bookshelf.R;

import java.util.ArrayList;

import list.ItemsAdapter;
import list.ShoppingCartAdapter;

public class ItemsActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        listView = findViewById(R.id.list_view);

        //TODO: itemIDs/list should obtain from database.
        String[] itemIDs = new String[]{"11111111", "22222222", "33333333"};
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < itemIDs.length; ++i) {
            list.add(itemIDs[i]);
        }

        ItemsAdapter adapter = new ItemsAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String itemID = list.get(position);
            Intent intent = new Intent(ItemsActivity.this, ItemActivity.class);
            intent.putExtra("itemID", itemID);
            startActivity(intent);
            }
        });
    }
}
