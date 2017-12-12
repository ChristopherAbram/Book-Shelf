package com.bookshelf.activity.authorized.payment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bookshelf.R;
import com.bookshelf.application.Constants;
import com.bookshelf.data.Item;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        Item shopItem = (Item) extras.getParcelable("item");

        if(extras != null) {
            int itemId = extras.getInt("itemId");
            String refId = extras.getString("refId");
            String customerName = extras.getString("customerName");
            String time = extras.getString("time");

            //TextView resultText = (TextView) findViewById(R.id.result_text);
            //resultText.setText(results[7]);

            TextView timeText = (TextView) findViewById(R.id.time);
            timeText.setText(time);

            TextView refIdText = (TextView) findViewById(R.id.ref_id);
            refIdText.setText(getString(R.string.ref_id) + refId);

            TextView customerNameText = (TextView) findViewById(R.id.customer_name);
            customerNameText.setText(customerName);

            TextView shopItemText = (TextView) findViewById(R.id.shop_item);
            shopItemText.setText(shopItem.getName());

            TextView quantity = (TextView) findViewById(R.id.quantity);
            quantity.setText(getString(R.string.quantity) + shopItem.getAmount());

            TextView totalPrice = (TextView) findViewById(R.id.total_price);
            totalPrice.setText(shopItem.getTotalPrice() + " " + Constants.DEFAULT_CURRENCY.getCode());
        }else {
            super.onBackPressed();
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void confirmButton(View view) {
        finish();
    }
}
