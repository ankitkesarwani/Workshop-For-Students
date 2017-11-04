package com.example.kesar.workshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Signin extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;

    private TextInputLayout mEmail;
    private TextInputLayout mPassword;

    private Button mSigninBtn;

    private DatabaseHelper mDatabaseHelper;

    private ProgressDialog mSigninProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mToolbar = (Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initListeners();
        initObjects();

    }

    private void initViews() {

        mEmail = (TextInputLayout) findViewById(R.id.signin_email);
        mPassword = (TextInputLayout)findViewById(R.id.signin_password);

        mSigninBtn = (Button) findViewById(R.id.signin_btn);

    }

    private void initListeners() {

        mSigninBtn.setOnClickListener(this);

    }

    private void initObjects() {

        mSigninProgress = new ProgressDialog(this);
        mDatabaseHelper = new DatabaseHelper(Signin.this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.signin_btn:
                verifyFromSQLite();
                break;

        }

    }

    private void verifyFromSQLite() {

        String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

            mSigninProgress.setTitle("Signing in");
            mSigninProgress.setMessage("Please wait while we checking your credentials");
            mSigninProgress.setCanceledOnTouchOutside(false);
            mSigninProgress.show();

            if (mDatabaseHelper.checkUser(email, password)) {

                mSigninProgress.dismiss();

                Intent signinIntent = new Intent(Signin.this, MainActivity.class);
                signinIntent.putExtra("EMAIL", email);
                startActivity(signinIntent);

            } else {

                mSigninProgress.dismiss();

                Toast.makeText(Signin.this, "Email or Password is incorrect. Please try again", Toast.LENGTH_LONG).show();

            }

        } else {

            Toast.makeText(Signin.this, "Error while Logged you in...please try again", Toast.LENGTH_LONG).show();

        }

    }

}
