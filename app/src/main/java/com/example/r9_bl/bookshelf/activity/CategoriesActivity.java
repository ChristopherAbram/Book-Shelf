package com.example.r9_bl.bookshelf.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.r9_bl.bookshelf.R;

import java.util.ArrayList;

import list.CategoriesAdapter;
import list.ShoppingCartAdapter;

public class CategoriesActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        listView = findViewById(R.id.list_view);

        //TODO: categoriesList/list should obtain from database.
        String[] categoriesList = new String[]{"Horror", "Action", "Mystery"};
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < categoriesList.length; ++i) {
            list.add(categoriesList[i]);
        }

        CategoriesAdapter adapter = new CategoriesAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get list row data (now String as a phone name)
                String phone = list.get(position);
                // create an explicit intent
                Intent intent = new Intent(CategoriesActivity.this, ItemsActivity.class);
                // add data to intent
                intent.putExtra("phone",phone);
                // start a new activity
                startActivity(intent);
            }
        });
    }
}
