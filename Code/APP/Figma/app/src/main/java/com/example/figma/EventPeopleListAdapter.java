package com.example.figma;

import android.content.Context;
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

public class EventPeopleListAdapter extends RecyclerView.Adapter<EventPeopleListAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Personal> personal_list;
    private Context mContext;
    public Personal event_personal;

    public EventPeopleListAdapter(Context context) {
        mContext = context;
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
                .inflate(R.layout.f4_5_1_rec_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(EventPeopleListAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        event_personal = personal_list.get(position);
        String applicant = event_personal.getPersonal_number() + "/" + event_personal.getPersonal_name();
        String applicant_tag1 = event_personal.getPersonal_tag();
    //    String applicant_tag2 = personal.getPersonal_tag()[1];
      //  String applicant_tag3 = personal.getPersonal_tag()[2];
      //  String applicant_tag4 = personal.getPersonal_tag()[3];
        //Set values
        holder.event_applicant_image.setImageBitmap(event_personal.getPersonal_image());
        holder.event_applicant_view.setText(applicant);
        holder.event_applicant_tag1_view.setText("#"+applicant_tag1);
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
    class TaskViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        ImageView event_applicant_image;
        TextView event_applicant_view;
        TextView event_applicant_tag1_view;
        TextView event_applicant_tag2_view;
        TextView event_applicant_tag3_view;
        TextView event_applicant_tag4_view;
        Button event_applicant_detail;
        private Gson gson;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            event_applicant_image = itemView.findViewById(R.id.EventApplicantImageImageView);
            event_applicant_view = itemView.findViewById(R.id.EventApplicantTextView);
            event_applicant_tag1_view = itemView.findViewById(R.id.EventApplicantTag1TextView);
            event_applicant_tag2_view = itemView.findViewById(R.id.EventApplicantTag2TextView);
            event_applicant_tag3_view = itemView.findViewById(R.id.EventApplicantTag3TextView);
            event_applicant_tag4_view = itemView.findViewById(R.id.EventApplicantTag4TextView);
            itemView.setOnClickListener(v -> {

                Global.tempF7_studentid = personal_list.get(getAdapterPosition()).getPersonal_number();
                //Global.tempF7_studentid = event_personal.getPersonal_number();
//                    Log.d("Event people adapter","get student id :"+Global.tempF7_studentid);
                Navigation.findNavController(v).navigate(R.id.action_f4_5_1_to_f7);
            });
            event_applicant_detail = itemView.findViewById(R.id.EventApplicantDetailButton);
            event_applicant_detail.setVisibility(View.INVISIBLE);
//            event_applicant_detail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Global.tempF7_studentid = personal_list.get(getAdapterPosition()).getPersonal_number();
//                    //Global.tempF7_studentid = event_personal.getPersonal_number();
////                    Log.d("Event people adapter","get student id :"+Global.tempF7_studentid);
//                    Navigation.findNavController(v).navigate(R.id.action_f4_5_1_to_f7);
//                }
//            });

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

