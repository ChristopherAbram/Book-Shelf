package com.bookshelf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.bookshelf.R;
import com.bookshelf.data.Cart;
import com.bookshelf.data.Item;
import com.bookshelf.data.Order;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

    public class HistoryAdapter extends ArrayAdapter<Order>  {

    @BindView(R.id.date_text_view)
    TextView dateTextView;

    @BindView(R.id.name_text_view)
    TextView nameTextView;

    @BindView(R.id.amount_text_view)
    TextView amountTextView;

    @BindView(R.id.price_text_view)
    TextView priceTextView;

    @BindView(R.id.total_price_text_view)
    TextView totalPriceTextView;

    private Context context;
    private ArrayList<Order> orders;

    public HistoryAdapter(Context context, ArrayList<Order> orders) {
        super(context, R.layout.list_history, orders);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_history, parent, false);
        ButterKnife.bind(this, rowView);
        Order order = orders.get(position);

        dateTextView.setText(order.getCdate());
        nameTextView.setText(order.getName());
        amountTextView.setText(order.getAmount().toString());
        priceTextView.setText(order.getPrice().toString());
        totalPriceTextView.setText(order.getPrice() * ((float)order.getAmount()) + " EUR");

        return rowView;
    }

}