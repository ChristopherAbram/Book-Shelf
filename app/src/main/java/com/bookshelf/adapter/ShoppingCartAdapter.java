package com.bookshelf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bookshelf.R;

import java.util.ArrayList;

public class ShoppingCartAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> itemID;

    public ShoppingCartAdapter(Context context, ArrayList<String> itemID) {
        super(context, R.layout.list_shopping_cart, R.id.textView, itemID);
        this.context = context;
        this.itemID = itemID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_shopping_cart, parent, false);

        TextView nameTextView = rowView.findViewById(R.id.name_text_view);

        //TODO: categoryTextView should be Title of the item instead of ID. (obtain from database)
        nameTextView.setText(itemID.get(position));

        return rowView;
    }

}