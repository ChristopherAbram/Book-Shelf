package com.bookshelf.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.activity.BaseActivity;
import com.bookshelf.activity.ShoppingCartActivity;
import com.bookshelf.api.CartService;
import com.bookshelf.api.ItemService;
import com.bookshelf.data.Cart;
import com.bookshelf.data.Item;
import com.bookshelf.data.collection.Carts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class ShoppingCartAdapter extends ArrayAdapter<Cart>  {

    @BindView(R.id.name_text_view)
    TextView nameTextView;

    @BindView(R.id.amount_text_view)
    TextView amountTextView;

    @BindView(R.id.price_text_view)
    TextView priceTextView;

    @BindView(R.id.remove_text_view)
    TextView removeTextView;

    private Context context;
    private ArrayList<Cart> carts;
    private ArrayList<Item> items;

    public ShoppingCartAdapter(Context context, ArrayList<Cart> carts, ArrayList<Item> items) {
        super(context, R.layout.list_shopping_cart, carts);
        this.context = context;
        this.carts = carts;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_shopping_cart, parent, false);
        ButterKnife.bind(this, rowView);
        Cart cart = carts.get(position);

        nameTextView.setText(items.);
        amountTextView.setText(cart.getAmount().toString());

        return rowView;
    }

}