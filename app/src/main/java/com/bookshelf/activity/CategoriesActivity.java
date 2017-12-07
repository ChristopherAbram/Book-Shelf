package com.bookshelf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bookshelf.R;
import com.example.r9_bl.bookshelf.activity.ItemsActivity;

import java.util.ArrayList;

import list.CategoriesAdapter;

public class CategoriesActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        listView = findViewById(R.id.list_view);

        //TODO: categoriesList/list should obtain from database.
        String[] categoriesIDs = new String[]{"001", "002", "003"};
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < categoriesIDs.length; ++i) {
            list.add(categoriesIDs[i]);
        }

        CategoriesAdapter adapter = new CategoriesAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String categoryID = list.get(position);
            Intent intent = new Intent(CategoriesActivity.this, ItemsActivity.class);
            intent.putExtra("categoryID", categoryID);
            startActivity(intent);
            }
        });
    }
}
