package com.example.r9_bl.bookshelf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.r9_bl.bookshelf.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import data.CsrfToken;
import data.User;
import service.AuthenticationService;
import service.receiver.RequestResultListener;
import service.receiver.RequestResultReceiver;

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

    private RequestResultReceiver mResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        // Instantiate RequestResultReceiver and listener:
        mResultReceiver = new RequestResultReceiver(new Handler());
        mResultReceiver.setRequestResultListener(new RequestResultListener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if(resultData.containsKey("csrf")) {
                    CsrfToken csrf = (CsrfToken) resultData.getParcelable("csrf");
                    Toast.makeText(SignInActivity.this, csrf.getToken(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // Set up click listener:
        mSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Get user data:
                User user = getUserDataFromView();

                Log.d(TAG, user.getEmail() + ", " + user.getPassword());

                // Set up intent:
                Intent intent = new Intent(SignInActivity.this, AuthenticationService.class);
                intent.putExtra("user", user);
                intent.putExtra("receiver", mResultReceiver);
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
