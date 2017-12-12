package com.bookshelf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bookshelf.R;
import com.bookshelf.data.Cart;
import com.bookshelf.data.Item;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingCartAdapter extends ArrayAdapter<Cart>  {

    @BindView(R.id.image_view)
    ImageView imageView;

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
        Item item = findItemById(items, cart.getItemId());

        nameTextView.setText(item.getName());
        amountTextView.setText(cart.getAmount().toString());
        priceTextView.setText(item.getPrice().toString());

        if(!item.getPicture().isEmpty()) {
            Glide.with(context).load("https://bookshelf.krzysztofabram.pl/images/miniatures/"+item.getPicture()+".png").into(imageView);
        }else {
            Glide.with(context).load("https://bookshelf.krzysztofabram.pl/images/miniatures/000000.png").into(imageView);
        }

        return rowView;
    }


    public Item findItemById(ArrayList<Item> items, int id) {
        for(Item item : items) {
            if(item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
}