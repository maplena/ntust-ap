package com.example.figma;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class ApplicantManageAdapter extends RecyclerView.Adapter<ApplicantManageAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Personal> personal_list;
    private Context mContext;
    public Personal post_personal;
    private Event toevent;

    public ApplicantManageAdapter(Context context,Event event)
    {
        mContext = context;
        toevent = event;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.f4_2_2_rec_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(ApplicantManageAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        post_personal = personal_list.get(position);
        String applicant = post_personal.getPersonal_number() + "/" + post_personal.getPersonal_name();
        String applicant_tag1 = post_personal.getPersonal_tag();
       /* String applicant_tag2 = personal.getPersonal_tag()[1];
        String applicant_tag3 = personal.getPersonal_tag()[2];
        String applicant_tag4 = personal.getPersonal_tag()[3];*/
        //Set values

        holder.applicant_image.setImageBitmap(post_personal.getPersonal_image());
        holder.applicant_view.setText(post_personal.getPersonal_number() + "/" + post_personal.getPersonal_name());
        holder.applicant_tag1_view.setText(applicant_tag1);

    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (personal_list == null) {
            return 0;
        }
        return personal_list.size();
    }

    public List<Personal> getTasks() {
        return personal_list;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Personal> taskEntries) {
        personal_list = taskEntries;
        notifyDataSetChanged();
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder{
        private Gson gson;
        // Class variables for the task description and priority TextViews
        ImageView applicant_image;
        TextView applicant_view;
        TextView applicant_tag1_view;
        TextView applicant_tag2_view;
        TextView applicant_tag3_view;
        TextView applicant_tag4_view;
        Button applicant_detail;
        Button applicant_kick;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            applicant_image = itemView.findViewById(R.id.ApplicantManageImage);
            applicant_view = itemView.findViewById(R.id.ApplicantTextView);
            applicant_tag1_view = itemView.findViewById(R.id.ApplicantTag1TextView);
//            applicant_tag2_view = itemView.findViewById(R.id.ApplicantTag2TextView);
//            applicant_tag3_view = itemView.findViewById(R.id.ApplicantTag3TextView);
//            applicant_tag4_view = itemView.findViewById(R.id.ApplicantTag4TextView);
            applicant_detail = itemView.findViewById(R.id.ApplicantDetailButton);
            applicant_detail.setOnClickListener(v -> {
                Global.tempF7_studentid = post_personal.getPersonal_number();
                Navigation.findNavController(v).navigate(R.id.action_f4_2_2_to_f7);
            });

            applicant_kick = itemView.findViewById(R.id.ApplicantKickButton);
            applicant_kick.setOnClickListener(v -> {
                Bundle data = new Bundle();
                String personaljson = getGsonParser().toJson(post_personal);
                String eventjson = getGsonParser().toJson(toevent);
                data.putString("personal",personaljson);
                data.putString("event",eventjson);
                Navigation.findNavController(v).navigate(R.id.action_f4_2_2_to_f4_2_2_d2,data);
            });
        }
        public Gson getGsonParser() {
            if(null == gson) {
                GsonBuilder builder = new GsonBuilder();
                gson = builder.create();
            }
            return gson;
        }
    }
}
