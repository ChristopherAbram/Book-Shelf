package com.bookshelf.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.adapter.HistoryAdapter;
import com.bookshelf.api.OrderService;
import com.bookshelf.data.Order;
import com.bookshelf.data.collection.Orders;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class HistoryActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        showProgressBar();
        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("userId");
    }

    @Override
    public void onStart() {
        super.onStart();
        OrderService orderService = generateCallService(OrderService.class);
        Call<Orders> call = orderService.getOrdersByFilter("user_id,eq,"+userId);
        call.enqueue(new OrdersCallback());
    }

    private class OrdersCallback extends Callback<Orders> {

        @Override
        public void onResponse(Call<Orders> call, Response<Orders> response) {
            super.onResponse(call, response);
            if(response.isSuccessful()){
                final ArrayList<Order> list = response.body().getOrders();
                if(!list.isEmpty()) {
                    HistoryAdapter adapter = new HistoryAdapter(getBaseContext(), list);
                    listView.setAdapter(adapter);
                }
                hideProgressBar();
            }
            else {
                Toast.makeText(HistoryActivity.this, "Unable to get history...", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Orders> call, Throwable t) {
            super.onFailure(call, t);
            Toast.makeText(HistoryActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }

    }
}
