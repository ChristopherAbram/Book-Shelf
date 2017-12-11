package com.bookshelf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.activity.authorized.HomeActivity;
import com.bookshelf.adapter.CategoriesAdapter;
import com.bookshelf.api.CategoryService;
import com.bookshelf.api.ItemService;
import com.bookshelf.api.UserService;
import com.bookshelf.data.Category;
import com.bookshelf.data.Item;
import com.bookshelf.data.User;
import com.bookshelf.data.collection.Categories;
import com.bookshelf.data.collection.Items;
import com.bookshelf.data.collection.Users;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Response;

public class ItemActivity extends BaseActivity {

    Item item = null;
    User merchant = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        showProgressBar();

        Bundle extras = getIntent().getExtras();
        item = (Item) extras.get("item");
    }

    @Override
    protected void onStart(){
        super.onStart();

        ItemService service = generateCallService(ItemService.class);

        TextView nameTextView = findViewById(R.id.name_text_view);
        nameTextView.setText(item.getName());

        TextView priceTextView = findViewById(R.id.price_text_view);
        priceTextView.setText(item.getPrice()+" EUR");

        TextView longDescTextView = findViewById(R.id.long_desc_text_view);
        longDescTextView.setText(item.getDescription());

        Call<User> callUser = service.getUserByID(item.getUserId());
        callUser.enqueue(new UserCallback());

        Call<Category> callCategory = service.getCategoriesByID(item.getCategoryId());
        callCategory.enqueue(new CategoryCallback());
    }

    private class UserCallback extends Callback<User> {

        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            super.onResponse(call, response);
            if(response.isSuccessful()){
                TextView merchantName = findViewById(R.id.merchant_name);
                merchantName.setText(response.body().getFirstname()+" "+response.body().getLastname());
                merchant = response.body();
            }
            else
                Toast.makeText(ItemActivity.this, "Unable to get user...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            super.onFailure(call, t);
            Toast.makeText(ItemActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }

    }

    private class CategoryCallback extends Callback<Category> {

        @Override
        public void onResponse(Call<Category> call, Response<Category> response) {
            super.onResponse(call, response);
            if(response.isSuccessful()){
                TextView categoryTextView = findViewById(R.id.category_text_view);
                categoryTextView.setText(response.body().getName());
                hideProgressBar();

                View merchantButton = findViewById(R.id.merchant_button);

                merchantButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(ItemActivity.this, AccountActivity.class);
                        intent.putExtra("userId", merchant.getId());
                        startActivity(intent);
                    }
                });
            }
            else
                Toast.makeText(ItemActivity.this, "Unable to get category...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Category> call, Throwable t) {
            super.onFailure(call, t);
            Toast.makeText(ItemActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }

    }

}
