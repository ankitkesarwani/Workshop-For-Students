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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_workshop);

        mToolbar = (Toolbar) findViewById(R.id.workshop_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Workshops");

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
        workshopRecyclerAdapter = new WorkshopRecyclerAdapter(listWorkshop);

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

}
