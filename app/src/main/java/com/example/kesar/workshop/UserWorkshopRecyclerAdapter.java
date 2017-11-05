package com.example.kesar.workshop;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kesar on 11/5/2017.
 */

public class UserWorkshopRecyclerAdapter extends RecyclerView.Adapter<UserWorkshopRecyclerAdapter.WorkshopViewHolder> {

    private List<DashboardWorkshop> listWorkshops;
    DatabaseHelper databaseHelper;

    protected String session;

    public UserWorkshopRecyclerAdapter(List<DashboardWorkshop> listWorkshops, String session) {

        this.listWorkshops = listWorkshops;
        this.session = session;

    }

    @Override
    public WorkshopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshop_applied_layout, parent, false);
        return new WorkshopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WorkshopViewHolder holder, final int position) {

        holder.workshop_name.setText(listWorkshops.get(position).getWorkshop_name());
        holder.college_name.setText(listWorkshops.get(position).getCollege_name());
        holder.location.setText(listWorkshops.get(position).getLocation());
        holder.date.setText(listWorkshops.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        Log.v(UserWorkshopRecyclerAdapter.class.getSimpleName(),""+listWorkshops.size());
        return listWorkshops.size();
    }

    public class WorkshopViewHolder extends RecyclerView.ViewHolder {

        public TextView workshop_name;
        public TextView college_name;
        public TextView location;
        public TextView date;


        public WorkshopViewHolder(View view) {
            super(view);

            workshop_name = (TextView) view.findViewById(R.id.applied_workshop_name);
            college_name = (TextView) view.findViewById(R.id.applied_college_name);
            location = (TextView) view.findViewById(R.id.applied_location);
            date = (TextView) view.findViewById(R.id.applied_date_of_workshop);

        }

    }

}
