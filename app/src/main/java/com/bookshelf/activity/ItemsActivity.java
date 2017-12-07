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
import com.bookshelf.data.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ItemsActivity extends BaseActivity {

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

    @Override
    public void onStart(){
        super.onStart();

        ItemService service = generateCallService(ItemService.class);
        Call<List<Item>> call = service.getItems("1, 50");
        call.enqueue(new ItemCallback());
    }

    private class ItemCallback extends Callback<List<Item>> {
        @Override
        public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
            super.onResponse(call, response);

            if(response.isSuccessful()){
                Toast.makeText(ItemsActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(ItemsActivity.this, "Unable to get items...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<List<Item>> call, Throwable t) {
            super.onFailure(call, t);

            Toast.makeText(ItemsActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }
    }

}
