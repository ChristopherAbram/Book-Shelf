package com.bookshelf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        listView = findViewById(R.id.list_view);

        Bundle extras = getIntent().getExtras();
        category = (Category) extras.get("category");
        if(category != null) {
            setTitle("Category: "+category.getName());
        }

        searchKeyword = extras.getString("searchKeyword");
        if(searchKeyword != null) {
            setTitle("Search: "+searchKeyword);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        ItemService service = generateCallService(ItemService.class);
        if(category != null) {
            Call<Items> call = service.getItemByFilter("category_id,eq,"+category.getId());
            call.enqueue(new ItemCallback());
        }else if(searchKeyword != null) {
            Call<Items> call = service.getItemByFilter("name,cs,"+searchKeyword);
            call.enqueue(new ItemCallback());
        }
    }

    private class ItemCallback extends Callback<Items> {

        @Override
        public void onResponse(Call<Items> call, Response<Items> response) {
            super.onResponse(call, response);

            if(response.isSuccessful()){
                final ArrayList<Item>  list = response.body().getItems();
                ItemsAdapter adapter = new ItemsAdapter(getBaseContext(), list);
                listView.setAdapter(adapter);

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
