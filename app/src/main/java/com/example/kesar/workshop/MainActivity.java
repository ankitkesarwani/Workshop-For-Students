package com.example.kesar.workshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DatabaseHelper mDatabaseHelper;
    private User user;

    private String session = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Dashboard");

        mDatabaseHelper = new DatabaseHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        session = getIntent().getStringExtra("EMAIL");

        if(TextUtils.isEmpty(session)) {

            Intent mainIntent = new Intent(MainActivity.this, AvailableWorkshop.class);
            startActivity(mainIntent);

        }

    }
}
