package com.bookshelf.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.activity.authorized.HomeActivity;
import com.bookshelf.api.UserService;
import com.bookshelf.data.CurrentUser;
import com.bookshelf.data.FullUser;
import com.bookshelf.data.User;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class AccountActivity extends BaseActivity {

    FullUser user = null;
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
        Call<FullUser> callUser = userService.getFullUserByID(userId);
        Toast.makeText(AccountActivity.this, "userId: "+userId, Toast.LENGTH_LONG).show();
        callUser.enqueue(new FullUserCallback());
    }


    private class FullUserCallback extends Callback<FullUser> {

        @Override
        public void onResponse(Call<FullUser> call, Response<FullUser> response) {
            super.onResponse(call, response);
            if(response.isSuccessful()){
                user = response.body();
                firstnameLastnameTextView.setText(user.getFirstname()+" "+user.getLastname());
                emailTextView.setText(user.getEmail());
                genderTextView.setText(user.getSex().toString());
                birthdateTextView.setText(user.getBdate());
                hideProgressBar();
            }
            else
                Toast.makeText(AccountActivity.this, "Unable to get user...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<FullUser> call, Throwable t) {
            super.onFailure(call, t);
            Toast.makeText(AccountActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }

    }

}
