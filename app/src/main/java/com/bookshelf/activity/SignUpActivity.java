package com.bookshelf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.data.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.firstname)
    EditText firstname;

    @BindView(R.id.lastname)
    EditText lastname;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.sex)
    RadioGroup sex;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.confirm_password)
    EditText confirm_password;

    @BindView(R.id.bdate)
    EditText bdate;

    @BindView(R.id.sign_in)
    Button next;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    // Go to address:
                    toSignUpAddress(user);
                }
            }
        });
    }

    public void toSignUpAddress(User user) {
        Intent intent = new Intent(this, SignUpAddressActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    private boolean isValid(){
        // validate form...
        // form should not be empty..
        user = getUserFromView();
        if(!user.getPassword().equals(confirm_password.getText().toString())){
            // TODO: inform user that passwords are not the same
            Toast.makeText(getBaseContext(), "Password must be the same.", Toast.LENGTH_LONG).show();
            return false;
        }

        // TODO: Check validity of email, birth date and so on...

        return true;
    }

    private User getUserFromView(){
        User user = new User();

        user.setFirstname(firstname.getText().toString());
        user.setLastname(lastname.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setBdate(bdate.getText().toString());

        int selectedId = sex.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) findViewById(selectedId);
        Character s = rb.getText().toString().toLowerCase().equals("male") ? 'M' : 'F';
        user.setSex(s);

        return user;
    }


}
