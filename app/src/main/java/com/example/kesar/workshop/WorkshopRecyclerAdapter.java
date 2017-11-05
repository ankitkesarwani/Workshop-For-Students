package com.example.kesar.workshop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by kesar on 11/4/2017.
 */

public class WorkshopRecyclerAdapter extends RecyclerView.Adapter<WorkshopRecyclerAdapter.WorkshopViewHolder> {

    private List<Workshop> listWorkshops;
    DatabaseHelper databaseHelper;
    private String session;

    private UserWorkshop userWorkshop;


    public WorkshopRecyclerAdapter(List<Workshop> listWorkshops, String session) {

        this.listWorkshops = listWorkshops;
        this.session = session;

        userWorkshop = new UserWorkshop();

    }

    @Override
    public WorkshopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshop_single_layout, parent, false);
        return new WorkshopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WorkshopViewHolder holder, final int position) {

        holder.workshop_name.setText(listWorkshops.get(position).getWorkshop_name());
        holder.college_name.setText(listWorkshops.get(position).getCollege_name());
        holder.location.setText(listWorkshops.get(position).getLocation());
        holder.date.setText(listWorkshops.get(position).getDate());

        holder.register_workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseHelper = new DatabaseHelper(v.getContext());

                if (TextUtils.isEmpty(session) && session == null) {

                    Intent moveToSignin = new Intent(v.getContext(), Signin.class);
                    v.getContext().startActivity(moveToSignin);

                } else {

                    String user_workshop_id = session;
                    int workshop_user_id = listWorkshops.get(position).getWorkshop_id();

                    userWorkshop.setUser_workshop_id(user_workshop_id);
                    userWorkshop.setWorkshop_user_id(workshop_user_id);

                    databaseHelper.addUserWorkshop(userWorkshop);

                    holder.register_workshop.setText("Applied");
                    holder.register_workshop.setEnabled(false);

                }

            }
        });

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


