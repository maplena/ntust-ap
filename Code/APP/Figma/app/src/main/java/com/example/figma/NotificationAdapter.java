package com.example.figma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Notification> notification_list;
    private Context mContext;


    public NotificationAdapter(Context context) {
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
                .inflate(R.layout.f2_1_notification_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(NotificationAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        Notification notification = notification_list.get(position);
        String notification_name = notification.getEvent_name();
        String notification_time = notification.getEvent_time();
        String notification_first_tag = notification.getEvent_tag()[0];
        String notification_second_tag = notification.getEvent_tag()[1];
        //Set values
        holder.notification_name_view.setText(notification_name);
        holder.notification_time_view.setText(notification_time);
        holder.notification_first_tag_view.setText(notification_first_tag);
        holder.notification_second_tag_view.setText(notification_second_tag);
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (notification_list == null) {
            return 0;
        }
        return notification_list.size();
    }

    public List<Notification> getTasks() {
        return notification_list;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Notification> taskEntries) {
        notification_list = taskEntries;
        notifyDataSetChanged();
    }

    // Inner class for creating ViewHolders
    static class TaskViewHolder extends RecyclerView.ViewHolder{

        // Class variables for the task description and priority TextViews
        TextView notification_name_view;
        TextView notification_time_view;
        TextView notification_first_tag_view;
        TextView notification_second_tag_view;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            notification_name_view = itemView.findViewById(R.id.NotificationNameTextView);
            notification_time_view = itemView.findViewById(R.id.NotificationTimeTextView);
            notification_first_tag_view = itemView.findViewById(R.id.NotificationTagTextView1);
            notification_second_tag_view = itemView.findViewById(R.id.NotificationTagTextView2);
        }

    }
}
