package com.example.kesar.workshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DatabaseHelper mDatabaseHelper;
    private User user;

    private RecyclerView mUserWorkshopList;
    private List<DashboardWorkshop> listWorkshop;
    private UserWorkshopRecyclerAdapter userWorkshopRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    private String session = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Dashboard");

        initViews();
        initObjects();

    }

    private void initViews() {

        mUserWorkshopList = (RecyclerView) findViewById(R.id.dashboard_workshop_list);

    }

    private void initObjects() {

        listWorkshop = new ArrayList<>();
        userWorkshopRecyclerAdapter = new UserWorkshopRecyclerAdapter(listWorkshop, session);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mUserWorkshopList.setLayoutManager(mLayoutManager);
        mUserWorkshopList.setItemAnimator(new DefaultItemAnimator());
        mUserWorkshopList.setHasFixedSize(true);
        mUserWorkshopList.setAdapter(userWorkshopRecyclerAdapter);
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.session = session;

        getDataFromSQLite();

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

    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

                listWorkshop.clear();
                listWorkshop.addAll(databaseHelper.getUserWorkshop(session));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                super.onPostExecute(aVoid);
                userWorkshopRecyclerAdapter.notifyDataSetChanged();

            }

        }.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_workshop) {

            Intent workshopIntent = new Intent(MainActivity.this, AvailableWorkshop.class);
            workshopIntent.putExtra("session", session);
            startActivity(workshopIntent);

        }

        return true;

    }
}
