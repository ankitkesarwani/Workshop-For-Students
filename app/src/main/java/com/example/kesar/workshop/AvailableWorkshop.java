package com.example.kesar.workshop;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class AvailableWorkshop extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;

    private Button mAddWorkshops;

    private RecyclerView mWorkshopList;
    private List<Workshop> listWorkshop;
    private WorkshopRecyclerAdapter workshopRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    protected String session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_workshop);

        mToolbar = (Toolbar) findViewById(R.id.workshop_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Workshops");

        session = getIntent().getStringExtra("session");

        initViews();
        initListeners();
        initObjects();

    }

    private void initViews() {

        mWorkshopList = (RecyclerView) findViewById(R.id.workshop_list);

        mAddWorkshops = (Button) findViewById(R.id.add_workshop_btn);

    }

    private void initListeners() {

        mAddWorkshops.setOnClickListener(this);

    }

    private void initObjects() {

        listWorkshop = new ArrayList<>();
        workshopRecyclerAdapter = new WorkshopRecyclerAdapter(listWorkshop, session);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mWorkshopList.setLayoutManager(mLayoutManager);
        mWorkshopList.setItemAnimator(new DefaultItemAnimator());
        mWorkshopList.setHasFixedSize(true);
        mWorkshopList.setAdapter(workshopRecyclerAdapter);
        databaseHelper = new DatabaseHelper(this);

        getDataFromSQLite();

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.add_workshop_btn:
                Intent availableWorkshop = new Intent(AvailableWorkshop.this, AddWorkshop.class);
                startActivity(availableWorkshop);
                break;

        }

    }

    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

                listWorkshop.clear();
                listWorkshop.addAll(databaseHelper.getAllWorkshop());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                super.onPostExecute(aVoid);
                workshopRecyclerAdapter.notifyDataSetChanged();

            }

        }.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.workshop_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.workshop_dashboard) {

            if (!TextUtils.isEmpty(session)) {

                Intent workshopIntent = new Intent(AvailableWorkshop.this, MainActivity.class);
                startActivity(workshopIntent);

            } else {

                Intent workshopIntent = new Intent(AvailableWorkshop.this, Signin.class);
                startActivity(workshopIntent);

            }

        }

        return true;

    }

}
