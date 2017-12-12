package com.bookshelf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookshelf.R;
import com.bookshelf.data.Item;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsAdapter extends ArrayAdapter<Item> {

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.name_text_view)
    TextView nameTextView;

    @BindView(R.id.price_text_view)
    TextView priceTextView;

    private Context context;
    private ArrayList<Item> items;

    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.list_items, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_items, parent, false);
        ButterKnife.bind(this, rowView);
        Item item = items.get(position);
        nameTextView.setText(item.getName());
        priceTextView.setText(item.getPrice()+" EUR");
        if(!item.getPicture().isEmpty()) {
            Glide.with(context).load("https://bookshelf.krzysztofabram.pl/images/miniatures/"+item.getPicture()+".png").into(imageView);
        }else {
            Glide.with(context).load("https://bookshelf.krzysztofabram.pl/images/miniatures/000000.png").into(imageView);
        }
        return rowView;
    }

}