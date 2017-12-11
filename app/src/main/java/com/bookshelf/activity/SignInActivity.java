package com.bookshelf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.activity.authorized.HomeActivity;
import com.bookshelf.api.Authentication;
import com.bookshelf.application.Constants;
import com.bookshelf.data.CsrfToken;
import com.bookshelf.data.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class SignInActivity extends BaseActivity {

    private final String TAG = getClass().getName();

    @BindView(R.id.email)
    EditText mEmail;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.sign_in)
    Button mSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set up url to the server first:
        setBaseUrl(Constants.AUTH_SERVER_URL);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        // Set up click listener:
        mSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Blocking login button:
                mSignIn.setEnabled(false);
                // Get user data:
                User user = getUserDataFromView();

                Authentication auth = generateCallService(Authentication.class);
                Call<CsrfToken> call = auth.perform(user.getEmail(), user.getPassword());
                showProgressBar();
                call.enqueue(new AuthenticationCallback());
            }
        });
    }

    private User getUserDataFromView(){
        User user = new User();
        user.setEmail(mEmail.getText().toString());
        user.setPassword(mPassword.getText().toString());
        return user;
    }

    public void toSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void toHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private class AuthenticationCallback extends Callback<CsrfToken> {
        @Override
        public void onResponse(Call<CsrfToken> call, Response<CsrfToken> response) {
            super.onResponse(call, response); // do not forget to call it!

            if(response.isSuccessful()){
                CsrfToken csrf = response.body();

                getShopApplication().setCsrfToken(csrf);
                getShopApplication().setAuthorized(true);

                Log.d(TAG, csrf.getToken());
                toHome();
            }
            else {
                hideProgressBar();
                // Enable login button:
                mSignIn.setEnabled(true);
                // TODO: Inform user that has specified wrong credentials:
                Toast.makeText(SignInActivity.this, "User does not exist, wrong email or password", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<CsrfToken> call, Throwable t) {
            hideProgressBar();
            // Enable login button:
            mSignIn.setEnabled(true);
            // TODO: Inform user that cannot authenticate:
            Toast.makeText(SignInActivity.this, "Authentication failed, try again later", Toast.LENGTH_LONG).show();
        }
    }
}
