package com.example.figma;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class EndPostEventAdapter extends RecyclerView.Adapter<EndPostEventAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Event> event_list;
    private Context mContext;
    public Event end_post_event;


    public EndPostEventAdapter(Context context) {
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
                .inflate(R.layout.f4_3_1_rec_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(EndPostEventAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        end_post_event = event_list.get(position);
        String event_name = end_post_event.getEvent_name();
        String event_tag = end_post_event.getEvent_tag();
        String event_creator = end_post_event.getEvent_host();

        //Set values
        holder.end_post_event_name_view.setText(event_name);
        holder.end_post_event_tag1_view.setText(event_tag);
       // holder.end_post_event_tag2_view.setText(event_tag[1]);
        holder.end_post_event_creator_view.setText(event_creator);
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (event_list == null) {
            return 0;
        }
        return event_list.size();
    }

    public List<Event> getTasks() {
        return event_list;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Event> taskEntries) {
        event_list = taskEntries;
        notifyDataSetChanged();
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder{
        private Gson gson;
        // Class variables for the task description and priority TextViews
        TextView end_post_event_name_view;
        TextView end_post_event_tag1_view;
        TextView end_post_event_tag2_view;
        TextView end_post_event_creator_view;

        Button end_post_event_detail;
        Button end_post_event_feedback;
        Button end_post_event_delete;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            end_post_event_name_view = itemView.findViewById(R.id.EndPostEventNameTextView);
            end_post_event_tag1_view = itemView.findViewById(R.id.EndPostEventTag1TextView);
            end_post_event_tag2_view = itemView.findViewById(R.id.EndPostEventTag2TextView);
            end_post_event_creator_view = itemView.findViewById(R.id.EndPostEventCreatorTextView);
            end_post_event_detail = itemView.findViewById(R.id.EndPostEventDetailButton);
            end_post_event_detail.setOnClickListener(v -> {
                Event event = event_list.get(getAdapterPosition());
                Bundle data = new Bundle();
                String eventjson = getGsonParser().toJson(event);
                data.putString("event",eventjson);
                Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f4_3_1_1,data);
            });
            end_post_event_feedback = itemView.findViewById(R.id.EndPostEventFeedbackButton);
            end_post_event_feedback.setOnClickListener(v -> {
                Event event = event_list.get(getAdapterPosition());
                Bundle data = new Bundle();
                String eventjson = getGsonParser().toJson(event);
                data.putString("event",eventjson);
                Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f4_3_1_2,data);
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
