package com.bookshelf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class ItemActivity extends BaseActivity {

    Item item = null;
    User merchant = null;

    @BindView(R.id.name_text_view)
    TextView nameTextView;

    @BindView(R.id.price_text_view)
    TextView priceTextView;

    @BindView(R.id.merchant_name)
    TextView merchantName;

    @BindView(R.id.long_desc_text_view)
    TextView longDescTextView;

    @BindView(R.id.category_text_view)
    TextView categoryTextView;

    @BindView(R.id.merchant_button)
    LinearLayout merchantButton;

    @BindView(R.id.amount_number)
    TextView amountNumber;

    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        ButterKnife.bind(this);
        showProgressBar();

        amount = 1;
        Bundle extras = getIntent().getExtras();
        item = (Item) extras.get("item");
    }

    @Override
    protected void onStart(){
        super.onStart();

        nameTextView.setText(item.getName());
        longDescTextView.setText(item.getDescription());
        priceTextView.setText(item.getPrice().toString());

        ItemService service = generateCallService(ItemService.class);

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
                categoryTextView.setText(response.body().getName());
                hideProgressBar();
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

    public void onClickPlus(View view) {
        if(amount < 100) {
            amount++;
            amountNumber.setText(amount+"");
        }
    }

    public void onClickMinus(View view) {
        if(amount > 1) {
            amount--;
            amountNumber.setText(amount+"");
        }
    }

}
