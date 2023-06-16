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

import java.util.ArrayList;
import java.util.List;


public class EndEventAdapter extends RecyclerView.Adapter<EndEventAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Event> end_event_list = new ArrayList<>();
    private Context mContext;
    public Event end_event;



    public EndEventAdapter(Context context) {
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
                .inflate(R.layout.f4_1_2_rec_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(EndEventAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        end_event = end_event_list.get(position);
        String event_name = end_event.getEvent_name();
        String event_tag = end_event.getEvent_tag();
        String event_creator = end_event.getEvent_host();
        //Set values
        holder.end_event_name_view.setText(event_name);
        holder.end_event_tag1_view.setText("#" + event_tag);
       // holder.end_event_tag2_view.setText("#" + event_tag[1]);
        holder.end_event_creator_view.setText(event_creator);
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (end_event_list == null) {
            return 0;
        }
        return end_event_list.size();
    }

    public List<Event> getTasks() {
        return end_event_list;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Event> taskEntries) {
        end_event_list = taskEntries;
        notifyDataSetChanged();
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder{

        // Class variables for the task description and priority TextViews
        TextView end_event_name_view;
        TextView end_event_tag1_view;
        TextView end_event_tag2_view;
        TextView end_event_creator_view;
        Button end_event_score;
        private Gson gson;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            end_event_name_view = itemView.findViewById(R.id.EndEventNameTextView);
            end_event_tag1_view = itemView.findViewById(R.id.EndEventTag1TextView);
            end_event_tag2_view = itemView.findViewById(R.id.EndEventTag2TextView);
            end_event_creator_view = itemView.findViewById(R.id.EndEventCreatorTextView);
            end_event_score = itemView.findViewById(R.id.EndEventScoreButton);
            end_event_score.setOnClickListener(v -> {
                Event event = end_event_list.get(getAdapterPosition());
                Bundle data = new Bundle();
                String eventjson = getGsonParser().toJson(event);
                data.putString("event",eventjson);
                Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f4_1_2_1,data);
            });

            itemView.setOnClickListener(v -> {
                Event event = end_event_list.get(getAdapterPosition());
                Bundle test = new Bundle();
                String eventjson = getGsonParser().toJson(event);
                test.putString("event",eventjson);
                test.putString("enter","unstarteventlist");
                Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f4_5,test);
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

