package com.example.r9_bl.bookshelf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.r9_bl.bookshelf.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import data.User;
import service.AuthenticationService;

public class SignInActivity extends Activity {

    private final String TAG = getClass().getName();

    @BindView(R.id.token)
    TextView mToken;

    @BindView(R.id.email)
    EditText mEmail;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.sign_in)
    Button mSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        mSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Get user data:
                User user = getUserDataFromView();

                Log.d(TAG, user.getEmail() + ", " + user.getPassword());

                // Set up intent:
                Intent intent = new Intent(SignInActivity.this, AuthenticationService.class);
                intent.putExtra("user", user);
                startService(intent);
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

}
