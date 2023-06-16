package com.example.figma;


import static com.example.figma.api.RetrofitClient.BASE_URL;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Event> event_list;
    private Context mContext;
    public Event eventa;



    public EventAdapter(Context context) {
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
                .inflate(R.layout.f4_1_rec_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(EventAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data

        eventa = event_list.get(position);
//        Picasso.with(mContext.getApplicationContext())
//                .load(BASE_URL+"api/v1/activity/getPhoto/"+eventa.getEvent_id())
//                .fetch();
        String event_name = eventa.getEvent_name();

        String event_time = eventa.DateToSimpleString(eventa.getEvent_start_date());
        String event_tag = eventa.getEvent_tag();
        String event_creator = eventa.getEvent_host();
        Integer event_max_people = eventa.getMax_people();
        Integer event_now_people = eventa.getNow_people();
        //Set values
        holder.event_list_name_view.setText(event_name);
        holder.event_list_date_view.setText(event_time);
        holder.event_list_tag1_view.setText("#" + event_tag);
        //holder.event_list_tag2_view.setText("#" + event_tag[1]);
        holder.event_list_creator_view.setText(event_creator);
        holder.event_list_people_view.setText("人數: " + event_now_people + "/" + event_max_people);

        Picasso.with(mContext.getApplicationContext())
                .load(BASE_URL+"api/v1/activity/getPhoto/"+eventa.getEvent_id())
                .fit()
                .centerCrop()
                .into(holder.event_list_image_view, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.event_list_image_view.setForeground(null);
                    }

                    @Override
                    public void onError() {

                        Picasso.with(mContext.getApplicationContext())
                                .load(R.drawable.noimage)
                                .fit()
                                .centerCrop()
                                .into(holder.event_list_image_view);

                    }

                });





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

        // Class variables for the task description and priority TextViews
        ImageView event_list_image_view;
        TextView event_list_name_view;
        TextView event_list_tag1_view;
        TextView event_list_tag2_view;
        TextView event_list_creator_view;
        TextView event_list_people_view;
        TextView event_list_date_view;
        private Gson gson;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            event_list_image_view = itemView.findViewById(R.id.EventListImageView);

            event_list_name_view = itemView.findViewById(R.id.EventListNameTextView);
            event_list_tag1_view = itemView.findViewById(R.id.EventListTagTextView1);
            event_list_tag2_view = itemView.findViewById(R.id.EventListTagTextView2);
            event_list_creator_view = itemView.findViewById(R.id.EventListCreatorTextView);
            event_list_people_view = itemView.findViewById(R.id.EventListPeopleTextView);
            event_list_date_view = itemView.findViewById(R.id.EventListDateTextView);

            itemView.setOnClickListener(v -> {
                Event event = event_list.get(getAdapterPosition());
                Bundle event_data = new Bundle();
                String eventjson = getGsonParser().toJson(event);
                event_data.putString("event",eventjson);
                event_data.putString("enter","eventlist");
                Navigation.findNavController(v).navigate(R.id.action_f4_1_to_f4_5,event_data);
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