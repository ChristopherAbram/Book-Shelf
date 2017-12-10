package com.bookshelf.activity.authorized;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bookshelf.R;
import com.bookshelf.api.Authentication;
import com.bookshelf.data.Exit;

import retrofit2.Call;
import retrofit2.Response;

public class SignOutActivity extends AuthorizedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);

        showProgressBar();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Authentication auth = generateCallService(Authentication.class);
        Call<Exit> call = auth.exit("1");

        call.enqueue(new ExitCallback());
    }

    private class ExitCallback extends Callback<Exit> {
        @Override
        public void onResponse(Call<Exit> call, Response<Exit> response) {
            super.onResponse(call, response);

            Intent intent = new Intent();

            if(response.isSuccessful()){
                Exit exit = response.body();
                if(exit.getExit().equals("successful")){
                    getShopApplication().setAuthorized(false);
                    intent.putExtra("result", "success");
                    setResult(Activity.RESULT_OK, intent);
                }
                else {
                    intent.putExtra("result", "Unable to logout.");
                    setResult(Activity.RESULT_CANCELED, intent);
                }
            }
            else {
                intent.putExtra("result", "Unable to logout.");
                setResult(Activity.RESULT_CANCELED, intent);
            }
            finish();
        }

        @Override
        public void onFailure(Call<Exit> call, Throwable t) {
            super.onFailure(call, t);
            // Go to home activity:
            Intent intent = new Intent();
            intent.putExtra("result", "Problem to logout - check your internet connection.");
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }
    }
}
