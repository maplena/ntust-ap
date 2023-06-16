package com.example.figma;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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


public class PostQuestionListAdapter extends RecyclerView.Adapter<PostQuestionListAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Question> question_list;
    private Context mContext;
    public Question question;



    public PostQuestionListAdapter(Context context) {
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
                .inflate(R.layout.f5_2_rec_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(PostQuestionListAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        question = question_list.get(position);
        String title = question.getTitle();
        String host = question.getHost();
        Log.d("onBindViewHolder","title:"+title+"host"+host);
        //Set values
        holder.post_question_list_name_view.setText(title);
        holder.post_question_list_creator_view.setText(host);
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (question_list == null) {
            return 0;
        }
        return question_list.size();
    }

    public List<Question> getTasks() {
        return question_list;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Question> taskEntries) {
        question_list = taskEntries;
        notifyDataSetChanged();
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder{
        private Gson gson;
        // Class variables for the task description and priority TextViews
        TextView post_question_list_name_view;
        TextView post_question_list_limit_view;
        TextView post_question_list_time_view;
        TextView question_list_view;
        TextView post_question_list_creator_view;

        Button post_question_list_revise;
        Button post_question_list_end;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            post_question_list_name_view = itemView.findViewById(R.id.PostQuestionListNameTextView);
            post_question_list_limit_view = itemView.findViewById(R.id.PostQuestionListLimitTextView);
            post_question_list_time_view = itemView.findViewById(R.id.PostQuestionListTimeTextView);
            post_question_list_creator_view = itemView.findViewById(R.id.PostQuestionListCreatorTextView);


            post_question_list_revise = itemView.findViewById(R.id.PostQuestionListReviseButton);
            post_question_list_revise.setOnClickListener(v -> {
                //TODO:傳送點選的問券
                question = question_list.get(getAdapterPosition());
                Bundle data = new Bundle();
                String questionJson = getGsonParser().toJson(question);
                data.putString("question",questionJson);
                Navigation.findNavController(v).navigate(R.id.action_f5_2_to_f5_2_3,data);
            });

            post_question_list_end = itemView.findViewById(R.id.PostQuestionListEndButton);
            post_question_list_end.setOnClickListener(v -> {
                question = question_list.get(getAdapterPosition());
                Bundle data = new Bundle();
                String questionJson = getGsonParser().toJson(question);
                data.putString("question",questionJson);
                Navigation.findNavController(v).navigate(R.id.action_f5_2_to_f5_2_1_1,data);
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

