package com.bookshelf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.bookshelf.R;
import com.bookshelf.data.Category;

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter<Category> {

    private Context context;
    private ArrayList<Category> categories = null;

    public CategoriesAdapter(Context context, ArrayList<Category> categories) {
        super(context, R.layout.list_categories, categories);
        this.context = context;
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_categories, parent, false);

        Category category = categories.get(position);

        TextView categoryTextView = rowView.findViewById(R.id.category_text_view);
        categoryTextView.setText(category.getName());

        return rowView;
    }

}