package com.example.figma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainEventAdapter extends RecyclerView.Adapter<MainEventAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Event> unstart_event_list = new ArrayList<>();
    private Context mContext;
    public Event un_start_event;

    public MainEventAdapter(Context context) {
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
                .inflate(R.layout.f2_1_event_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(MainEventAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        un_start_event = unstart_event_list.get(position);
        String event_name = un_start_event.getEvent_name();
        String event_time = un_start_event.DateToSimpleString(un_start_event.getEvent_start_date());
        String event_position = un_start_event.getEvent_place();
        //Set values
        holder.event_name_view.setText(event_name);
        holder.event_time_view.setText(event_time);
        holder.event_position_view.setText(event_position);
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
    static class TaskViewHolder extends RecyclerView.ViewHolder{

        // Class variables for the task description and priority TextViews
        TextView event_name_view;
        TextView event_time_view;
        TextView event_position_view;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            event_name_view = itemView.findViewById(R.id.EventNameTextView);
            event_time_view= itemView.findViewById(R.id.EventTimeTextView);
            event_position_view= itemView.findViewById(R.id.EventPositionTextView);

            itemView.setOnClickListener(v -> {

            });
        }

    }
}