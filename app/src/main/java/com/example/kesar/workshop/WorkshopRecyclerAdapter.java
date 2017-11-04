package com.example.kesar.workshop;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by kesar on 11/4/2017.
 */

public class WorkshopRecyclerAdapter extends RecyclerView.Adapter<WorkshopRecyclerAdapter.WorkshopViewHolder> {

    private List<Workshop> listWorkshops;
    private ClickListener listener;


    public WorkshopRecyclerAdapter (List<Workshop> listWorkshops, ClickListener listener) {

        this.listWorkshops = listWorkshops;
        this.listener = listener;

    }

    @Override
    public WorkshopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshop_single_layout, parent, false);
        return new WorkshopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WorkshopViewHolder holder, int position) {

        holder.workshop_name.setText(listWorkshops.get(position).getWorkshop_name());
        holder.college_name.setText(listWorkshops.get(position).getCollege_name());
        holder.location.setText(listWorkshops.get(position).getLocation());
        holder.date.setText(listWorkshops.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        Log.v(WorkshopRecyclerAdapter.class.getSimpleName(),""+listWorkshops.size());
        return listWorkshops.size();
    }

    /**
     * ViewHolder class
     */

    public class WorkshopViewHolder extends RecyclerView.ViewHolder {

        public TextView workshop_name;
        public TextView college_name;
        public TextView location;
        public TextView date;

        public Button register_workshop;

        public WorkshopViewHolder(View view) {
            super(view);

            workshop_name = (TextView) view.findViewById(R.id.workshop_name);
            college_name = (TextView) view.findViewById(R.id.college_name);
            location = (TextView) view.findViewById(R.id.location);
            date = (TextView) view.findViewById(R.id.date_of_workshop);

            register_workshop = (Button) view.findViewById(R.id.apply_btn);

        }
    }

}


