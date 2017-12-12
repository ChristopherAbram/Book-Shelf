package com.bookshelf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bookshelf.R;

import java.util.ArrayList;

import com.bookshelf.adapter.CategoriesAdapter;
import com.bookshelf.api.CategoryService;
import com.bookshelf.api.ItemService;
import com.bookshelf.data.Category;
import com.bookshelf.data.collection.Categories;
import com.bookshelf.data.collection.Items;

import retrofit2.Call;
import retrofit2.Response;

public class CategoriesActivity extends BaseActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        showProgressBar();

        listView = findViewById(R.id.list_view);
    }

    @Override
    public void onStart(){
        super.onStart();
        CategoryService service = generateCallService(CategoryService.class);
        Call<Categories> call = service.getCategories();
        call.enqueue(new CategoryCallback());
    }

    private class CategoryCallback extends Callback<Categories> {

        @Override
        public void onResponse(Call<Categories> call, Response<Categories> response) {
            super.onResponse(call, response);
            if(response.isSuccessful()){
                final ArrayList<Category> list = response.body().getCategories();
                if(!list.isEmpty()) {
                    CategoriesAdapter adapter = new CategoriesAdapter(getBaseContext(), list);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Category category = list.get(position);
                            Intent intent = new Intent(CategoriesActivity.this, ItemsActivity.class);
                            intent.putExtra("category", category);
                            startActivity(intent);
                        }
                    });
                }
                hideProgressBar();
            }
            else
                Toast.makeText(CategoriesActivity.this, "Unable to get items...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Categories> call, Throwable t) {
            super.onFailure(call, t);
            Toast.makeText(CategoriesActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }

    }
}
