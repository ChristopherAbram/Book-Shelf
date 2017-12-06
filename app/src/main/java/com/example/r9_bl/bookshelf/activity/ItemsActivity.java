package com.example.r9_bl.bookshelf.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        String[] itemIDs = new String[]{"110-21234", "554-12687", "455-21536"};
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < itemIDs.length; ++i) {
            list.add(itemIDs[i]);
        }

        ItemsAdapter adapter = new ItemsAdapter(this, list);
        listView.setAdapter(adapter);
    }
}
