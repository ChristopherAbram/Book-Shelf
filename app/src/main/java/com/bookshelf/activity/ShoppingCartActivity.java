package com.bookshelf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bookshelf.R;

import java.util.ArrayList;

import com.bookshelf.adapter.ItemsAdapter;
import com.bookshelf.adapter.ShoppingCartAdapter;
import com.bookshelf.api.CartService;
import com.bookshelf.api.ItemService;
import com.bookshelf.data.Cart;
import com.bookshelf.data.Item;
import com.bookshelf.data.collection.Carts;
import com.bookshelf.data.collection.Items;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class ShoppingCartActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;

    @BindView(R.id.pay_button)
    Button payButton;

    private int userId;
    ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        showProgressBar();
        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("userId");
    }

    @Override
    public void onStart() {
        super.onStart();
        ItemService itemsService = generateCallService(ItemService.class);
        Call<Items> itemsCallcall = itemsService.getItems();
        itemsCallcall.enqueue(new ItemsCallback());

        CartService service = generateCallService(CartService.class);
        Call<Carts> call = service.getCartsByFilter("user_id,eq," + userId);
        call.enqueue(new CartsCallback());
    }

    private class CartsCallback extends Callback<Carts> {

        @Override
        public void onResponse(Call<Carts> call, Response<Carts> response) {
            super.onResponse(call, response);

            if(response.isSuccessful()){
                final ArrayList<Cart> list = response.body().getCarts();
                if(!list.isEmpty()) {
                    ShoppingCartAdapter adapter = new ShoppingCartAdapter(getBaseContext(), list, items);
                    listView.setAdapter(adapter);
                    hideProgressBar();
                }
            }
            else {
                Toast.makeText(ShoppingCartActivity.this, "Unable to get items...", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Carts> call, Throwable t) {
            super.onFailure(call, t);
            Toast.makeText(ShoppingCartActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }

    }

    private class ItemsCallback extends Callback<Items> {

        @Override
        public void onResponse(Call<Items> call, Response<Items> response) {
            super.onResponse(call, response);
            if(response.isSuccessful()){
                items = response.body().getItems();
            }
            else {
                Toast.makeText(ShoppingCartActivity.this, "Unable to get items...", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Items> call, Throwable t) {
            super.onFailure(call, t);
            Toast.makeText(ShoppingCartActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }

    }

}
