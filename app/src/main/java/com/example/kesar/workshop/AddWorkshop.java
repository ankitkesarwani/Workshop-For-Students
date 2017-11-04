package com.example.kesar.workshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddWorkshop extends AppCompatActivity implements View.OnClickListener{

    private  Toolbar mToolbar;

    private EditText mWorkshopname;
    private EditText mCollegeName;
    private EditText mLocation;
    private EditText mDate;

    private DatabaseHelper mDatabaseHelper;
    private Workshop workshop;

    private Button mAddWorkshopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workshop);

        mToolbar = (Toolbar) findViewById(R.id.add_workshop_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Workshop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initListeners();
        initObjects();

    }

    private void initViews() {

        mWorkshopname = (EditText) findViewById(R.id.add_workshop_name);
        mCollegeName = (EditText) findViewById(R.id.add_college_name);
        mLocation = (EditText) findViewById(R.id.add_location);
        mDate = (EditText) findViewById(R.id.add_date);

        mAddWorkshopBtn = (Button) findViewById(R.id.add_workshop_btn);

    }

    private void initListeners() {

        mAddWorkshopBtn.setOnClickListener(this);

    }

    private void initObjects() {

        mDatabaseHelper = new DatabaseHelper(this);
        workshop = new Workshop();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.add_workshop_btn:
                postDataToSQLite();
                break;

        }

    }

    private void postDataToSQLite() {

        String workshop_name = mWorkshopname.getText().toString().trim();
        String college_name = mCollegeName.getText().toString().trim();
        String location = mLocation.getText().toString().trim();
        String date = mDate.getText().toString();

        if(!TextUtils.isEmpty(workshop_name) || !TextUtils.isEmpty(college_name) || !TextUtils.isEmpty(location) ||!TextUtils.isEmpty(date)) {

            workshop.setWorkshop_name(workshop_name);
            workshop.setCollege_name(college_name);
            workshop.setLocation(location);
            workshop.setDate(date);

            mDatabaseHelper.addWorkshop(workshop);

            Toast.makeText(AddWorkshop.this, "Workshop Added Successfully", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(AddWorkshop.this, "Please fill all the required field...", Toast.LENGTH_LONG).show();

        }

    }

}
