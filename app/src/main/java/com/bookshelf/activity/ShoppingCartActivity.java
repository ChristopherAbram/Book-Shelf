package com.bookshelf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.activity.authorized.payment.CardFormActivity;
import com.bookshelf.adapter.ShoppingCartAdapter;
import com.bookshelf.api.CartService;
import com.bookshelf.api.ItemService;
import com.bookshelf.data.Cart;
import com.bookshelf.data.Item;
import com.bookshelf.data.collection.Carts;
import com.bookshelf.data.collection.Items;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class ShoppingCartActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;

    @BindView(R.id.pay_button)
    Button payButton;

    @BindView(R.id.price_text_view)
    TextView priceTextView;

    private int userId;
    ArrayList<Item> items;
    float totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        showProgressBar();
        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("userId");

        payButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Item item = new Item();
                item.setName("Bookshelf - Shopping Cart");
                item.setAmount(1);
                item.setPrice(totalPrice);
                Intent resultIntent = new Intent(getBaseContext(), CardFormActivity.class);
                resultIntent.putExtra("item", item);
                startActivity(resultIntent);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ItemService itemsService = generateCallService(ItemService.class);
        Call<Items> itemsCall = itemsService.getItems();
        itemsCall.enqueue(new ItemsCallback());
    }

    private class ItemsCallback extends Callback<Items> {

        @Override
        public void onResponse(Call<Items> call, Response<Items> response) {
            super.onResponse(call, response);
            if(response.isSuccessful()){
                items = response.body().getItems();
                CartService service = generateCallService(CartService.class);
                Call<Carts> cartsCall = service.getCartsByFilter("user_id,eq," + userId);
                cartsCall.enqueue(new CartsCallback());
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

    private class CartsCallback extends Callback<Carts> {

        @Override
        public void onResponse(Call<Carts> call, Response<Carts> response) {
            super.onResponse(call, response);
            if(response.isSuccessful()){
                final ArrayList<Cart> list = response.body().getCarts();
                if(!list.isEmpty()) {
                    for(Cart cart : list) {
                        for(Item item : items) {
                            if(item.getId().equals(cart.getId())) {
                                totalPrice += item.getPrice()*cart.getAmount();
                            }
                        }
                    }
                    totalPrice = Math.round(totalPrice);
                    priceTextView.setText(totalPrice+" EUR");
                    ShoppingCartAdapter adapter = new ShoppingCartAdapter(getBaseContext(), list, items);
                    listView.setAdapter(adapter);
                }
            }
            else {
                Toast.makeText(ShoppingCartActivity.this, "Unable to get items...", Toast.LENGTH_LONG).show();
            }
            hideProgressBar();
        }

        @Override
        public void onFailure(Call<Carts> call, Throwable t) {
            super.onFailure(call, t);
            Toast.makeText(ShoppingCartActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }

    }

}
