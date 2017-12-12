package com.bookshelf.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.api.UserService;
import com.bookshelf.data.FullUser;
import com.bookshelf.data.collection.FullUsers;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class AccountActivity extends BaseActivity {

    FullUser fullUser = null;
    int userId;

    @BindView(R.id.firstname_lastname_text_view)
    TextView firstnameLastnameTextView;

    @BindView(R.id.email_text_view)
    TextView emailTextView;

    @BindView(R.id.gender_text_view)
    TextView genderTextView;

    @BindView(R.id.birthdate_text_view)
    TextView birthdateTextView;

    @BindView(R.id.address_text_view)
    TextView addressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        showProgressBar();

        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("userId");
    }

    @Override
    public void onStart() {
        super.onStart();

        UserService userService = generateCallService(UserService.class);
        Call<FullUsers> callUser = userService.getFullUsersByID("id,eq,"+userId);
        callUser.enqueue(new FullUserCallback());
    }


    private class FullUserCallback extends Callback<FullUsers> {

        @Override
        public void onResponse(Call<FullUsers> call, Response<FullUsers> response) {
            super.onResponse(call, response);
            if(response.isSuccessful()){
                fullUser = response.body().getFullusers().get(0);
                firstnameLastnameTextView.setText(fullUser.getFirstname()+" "+fullUser.getLastname());
                emailTextView.setText(fullUser.getEmail());
                genderTextView.setText(fullUser.getSex()+"");
                birthdateTextView.setText(fullUser.getBdate());
                String address = "";
                address += checkIfNull(fullUser.getPhone()) + checkIfNull(fullUser.getStreet()) + checkIfNull(fullUser.getCity()) + checkIfNull(fullUser.getZip()) + fullUser.getCountry();
                addressTextView.setText(address);
                hideProgressBar();
            }
            else
                Toast.makeText(AccountActivity.this, "Unable to get user...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<FullUsers> call, Throwable t) {
            super.onFailure(call, t);
            Toast.makeText(AccountActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }

    }

    public String checkIfNull(String string) {
        if(string != null) {
            if(string.length() > 1) {
                return string+"\n";
            }
        }
        return "";
    }

}
