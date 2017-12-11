package com.bookshelf.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.adapter.ItemsAdapter;
import com.bookshelf.api.ItemService;
import com.bookshelf.data.Category;
import com.bookshelf.data.Item;
import com.bookshelf.data.collection.Items;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class ItemsActivity extends BaseActivity {

    ListView listView;
    Category category = null;
    String searchKeyword = null;
    String searchType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        showProgressBar();

        listView = findViewById(R.id.list_view);

        Bundle extras = getIntent().getExtras();

        category = (Category) extras.get("category");
        searchKeyword = extras.getString("searchKeyword");
        searchType = extras.getString("searchType");

        if(category != null) {
            setTitle("Category: "+category.getName());
        }else if(searchKeyword != null) {
            setTitle("Search: "+searchKeyword);
        }else if(searchType.equals("hot")) {
            setTitle("Hot Items");
        }else if(searchType.equals("sale")) {
            setTitle("Sale Items");
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        ItemService service = generateCallService(ItemService.class);
        if(category != null) {
            Call<Items> call = service.getItemByFilter("category_id,eq,"+category.getId());
            call.enqueue(new ItemsCallback());
        }else if(searchKeyword != null) {
            Call<Items> call = service.getItemByFilter("name,cs,"+searchKeyword);
            call.enqueue(new ItemsCallback());
        }else {
            Call<Items> call = service.getItems();
            call.enqueue(new ItemsCallback());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        ComponentName componentName = new ComponentName(getApplicationContext(), SearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        finish();
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                }
        );
        return true;
    }

    private class ItemsCallback extends Callback<Items> {

        @Override
        public void onResponse(Call<Items> call, Response<Items> response) {
            super.onResponse(call, response);

            if(response.isSuccessful()){
                final ArrayList<Item>  list = response.body().getItems();
                ItemsAdapter adapter = new ItemsAdapter(getBaseContext(), list);
                listView.setAdapter(adapter);
                hideProgressBar();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Item item = list.get(position);
                        Intent intent = new Intent(ItemsActivity.this, ItemActivity.class);
                        intent.putExtra("item", item);
                        startActivity(intent);
                    }
                });
            }
            else
                Toast.makeText(ItemsActivity.this, "Unable to get items...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Items> call, Throwable t) {
            super.onFailure(call, t);

            Toast.makeText(ItemsActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }
    }

}
