package com.bookshelf.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.data.User;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity {

    User user = null;

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
        Bundle extras = getIntent().getExtras();
        user = (User) extras.get("user");

        firstnameLastnameTextView.setText(user.getFirstname()+" "+user.getLastname());
        emailTextView.setText(user.getEmail());
        genderTextView.setText(user.getSex().toString());
        birthdateTextView.setText(user.getBdate());
    }

}
