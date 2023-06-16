package com.example.figma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.Eventtest.RatingItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class EndPostScoreAdapter extends RecyclerView.Adapter<EndPostScoreAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<RatingItem> ratingItemList;
    private RatingItem ratingItem;
    private Context mContext;


    public EndPostScoreAdapter(Context context) {
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
                .inflate(R.layout.f4_3_1_2_rec_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(EndPostScoreAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        // Determine the values of the wanted data
         ratingItem = ratingItemList.get(position);
        String comment = ratingItem.getComment();
        Long score = ratingItem.getRating();
        //Set values
        holder.end_post_score_feed_back_view.setText(comment);
        holder.score.setText("分數" + score.toString());

    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (ratingItemList == null) {
            return 0;
        }
        return ratingItemList.size();
    }

    public List<RatingItem> getTasks() {
        return ratingItemList;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<RatingItem> taskEntries) {
        ratingItemList = taskEntries;
        notifyDataSetChanged();
    }

    // Inner class for creating ViewHolders
    static class TaskViewHolder extends RecyclerView.ViewHolder{
        private Gson gson;
        // Class variables for the task description and priority TextViews
        TextView end_post_score_feed_back_view;
        TextView score;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            end_post_score_feed_back_view = itemView.findViewById(R.id.Comment);
            score = itemView.findViewById(R.id.Score);

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

