package com.example.kesar.workshop;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;

    private TextInputLayout mName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;

    private Button mRegisterBtn;

    private DatabaseHelper mDatabaseHelper;
    private User user;

    private ProgressDialog mRegisterProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create new account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initListeners();
        initObjects();

    }

    private void initViews() {

        mName = (TextInputLayout) findViewById(R.id.register_name);
        mEmail = (TextInputLayout) findViewById(R.id.register_email);
        mPassword = (TextInputLayout) findViewById(R.id.register_password);

        mRegisterBtn = (Button) findViewById(R.id.register_btn);

    }

    private void initListeners() {

        mRegisterBtn.setOnClickListener(this);

    }

    private void initObjects() {

        mRegisterProgress = new ProgressDialog(this);

        mDatabaseHelper = new DatabaseHelper(Register.this);
        user = new User();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.register_btn:
                postDataToSQLite();
                break;

        }

    }

    private void postDataToSQLite() {

        String name = mName.getEditText().getText().toString().trim();
        String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();

        if(!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

            mRegisterProgress.setTitle("Signing in");
            mRegisterProgress.setMessage("Please wait while we checking your credentials");
            mRegisterProgress.setCanceledOnTouchOutside(false);
            mRegisterProgress.show();

            if(!mDatabaseHelper.checkUser(email)) {

                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);

                mDatabaseHelper.addUser(user);

                mRegisterProgress.dismiss();

                Toast.makeText(Register.this, "Registraton Successfull", Toast.LENGTH_LONG).show();

            } else {

                mRegisterProgress.dismiss();
                Toast.makeText(Register.this, "This Email is already registered", Toast.LENGTH_LONG).show();

            }

        } else {

            Toast.makeText(Register.this, "Please fill all the required field...", Toast.LENGTH_LONG).show();

        }

    }

}
