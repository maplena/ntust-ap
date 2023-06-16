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


public class UnStartEventAdapter extends RecyclerView.Adapter<UnStartEventAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Event> unstart_event_list = new ArrayList<>();
    private Context mContext;
    public Event un_start_event;



    public UnStartEventAdapter(Context context) {
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
                .inflate(R.layout.f4_1_1_rec_item2, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(UnStartEventAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        un_start_event = unstart_event_list.get(position);
        String event_name = un_start_event.getEvent_name();
        String event_time = un_start_event.DateToSimpleString(un_start_event.getEvent_start_date());
        String event_tag = un_start_event.getEvent_tag();
        String event_creator = un_start_event.getEvent_host();
        Integer event_max_people = un_start_event.getMax_people();
        Integer event_min_people = un_start_event.getNow_people();
        //Set values
        holder.un_start_event_name_view.setText(event_name);
        holder.un_start_event_date_view.setText("舉辦日期: " + event_time);
        holder.un_start_event_tag1_view.setText("#" + event_tag);
        //holder.un_start_event_tag2_view.setText("#" + event_tag[1]);
        holder.un_start_event_creator_view.setText(event_creator);
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (unstart_event_list == null) {
            return 0;
        }
        return unstart_event_list.size();
    }

    public List<Event> getTasks() {
        return unstart_event_list;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Event> taskEntries) {
        unstart_event_list = taskEntries;
        notifyDataSetChanged();
    }




    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder{

        // Class variables for the task description and priority TextViews
        TextView un_start_event_name_view;
        TextView un_start_event_tag1_view;
        TextView un_start_event_tag2_view;
        TextView un_start_event_creator_view;
        TextView un_start_event_date_view;
        Button un_start_event_cancel;
        private Gson gson;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            un_start_event_name_view = itemView.findViewById(R.id.UnStartEventNameTextView);
            un_start_event_tag1_view = itemView.findViewById(R.id.UnStartEventTag1TextView);
            un_start_event_tag2_view = itemView.findViewById(R.id.UnStartEventTag2TextView);
            un_start_event_creator_view = itemView.findViewById(R.id.UnStartEventCreatorTextView);
            un_start_event_date_view = itemView.findViewById(R.id.UnStartEventDateTextView);
            un_start_event_cancel = itemView.findViewById(R.id.UnStartEventCancelButton);
            un_start_event_cancel.setOnClickListener(v -> {
                Event event = unstart_event_list.get(getAdapterPosition());
                Bundle test = new Bundle();
                String eventjson = getGsonParser().toJson(event);
                test.putString("event",eventjson);
                Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f4_4_3,test);
            });

            itemView.setOnClickListener(v -> {
                Event event = unstart_event_list.get(getAdapterPosition());
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
