package com.bookshelf.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.bookshelf.activity.BaseActivity;
import com.bookshelf.api.ItemService;
import com.bookshelf.data.Item;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by r9_bl on December 12 2017.
 */

public class BaseActivityExtender extends BaseActivity {

    @Override
    public void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        ItemService service = generateCallService(ItemService.class);
        Call<Item> call = service.getItemByID(extras.getInt("itemId"));
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                super.onResponse(call, response);
                if(response.isSuccessful()){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", response.body());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
                else {
                    Toast.makeText(BaseActivityExtender.this, "Unable to get items...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(BaseActivityExtender.this, "Ooopsss...", Toast.LENGTH_LONG).show();
            }
        });
    }

}
