package com.example.figma;

import android.content.Context;
import android.util.Log;
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

public class EndPostQuestionPeopleListAdapter extends RecyclerView.Adapter<EndPostQuestionPeopleListAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Personal> personal_list;
    private Context mContext;
    public Personal end_post_personal;

    public EndPostQuestionPeopleListAdapter(Context context) {
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
                .inflate(R.layout.f4_3_1_1_rec_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(EndPostQuestionPeopleListAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        end_post_personal = personal_list.get(position);
        String applicant = end_post_personal.getPersonal_number() + "/" + end_post_personal.getPersonal_name();
        String applicant_tag1 = end_post_personal.getPersonal_tag();
     //   String applicant_tag2 = personal.getPersonal_tag()[1];
      //  String applicant_tag3 = personal.getPersonal_tag()[2];
        String applicant_sex = end_post_personal.getPersonal_sex();
        //Set values
        holder.end_post_event_people_image.setImageBitmap(end_post_personal.getPersonal_image());
        holder.end_post_event_people_name_view.setText(applicant);
        holder.end_post_event_people_tag1_view.setText("#"+applicant_tag1);
        holder.end_post_event_people_sex_view.setText("#"+applicant_sex);
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

        // Class variables for the task description and priority TextViews
        ImageView end_post_event_people_image;
        TextView end_post_event_people_name_view;
        TextView end_post_event_people_tag1_view;
        TextView end_post_event_people_tag2_view;
        TextView end_post_event_people_tag3_view;
        TextView end_post_event_people_sex_view;
        Button end_post_event_people_detail;
        private Gson gson;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            end_post_event_people_image = itemView.findViewById(R.id.F4311PeopleImageView);
            end_post_event_people_name_view = itemView.findViewById(R.id.F4311NameTextView);
            end_post_event_people_tag1_view = itemView.findViewById(R.id.F4311Tag1TextView);
            end_post_event_people_tag2_view = itemView.findViewById(R.id.F4311Tag2TextView);
            end_post_event_people_tag3_view = itemView.findViewById(R.id.F4311Tag3TextView);
            end_post_event_people_sex_view = itemView.findViewById(R.id.F4311SexTextView);
            end_post_event_people_detail = itemView.findViewById(R.id.F4311DetailButton);
            if(end_post_event_people_detail==null) Log.d("EPEPLA","is null");
            else Log.d("EPEPLA","is not null");
            end_post_event_people_detail.setOnClickListener(v -> {
                Global.tempF7_studentid = end_post_personal.getPersonal_number();
                Navigation.findNavController(v).navigate(R.id.action_f4_3_1_1_to_f7);
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


