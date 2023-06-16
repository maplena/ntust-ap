package com.example.figma;

import static com.example.figma.api.RetrofitClient.BASE_URL;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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


public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Question> question_list;
    private Context mContext;
    public Question question;



    public QuestionListAdapter(Context context) {
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
                .inflate(R.layout.f5_1_rec_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(QuestionListAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        question = question_list.get(position);
        String survey_name = question.getTitle();
        String survey_time = question.DateToSimpleString(question.getStartDate());
//        String survey_tag = question.getTag();
//        String survey_creator = question.getHost();
        Integer event_max_people = question.getPeopleNumLimit();
        //Set values
        holder.question_list_name_view.setText(survey_name);
        holder.question_list_time_view.setText("截止:"+survey_time);
        holder.question_list_limit_view.setText("件數:"+event_max_people.toString());
        Picasso.with(mContext.getApplicationContext()).load(BASE_URL+"api/v1/survey/getPhoto/"+question.getId()).into(holder.question_list_image,new Callback() {
            @Override
            public void onSuccess() {
                // 圖片讀取完成
                Log.d("F51","取得圖片");
            }

            @Override
            public void onError() {
                // 圖片讀取失敗
            }
        });
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

        // Class variables for the task description and priority TextViews
        ImageView question_list_image;
        TextView question_list_name_view;
        TextView question_list_limit_view;
        TextView question_list_time_view;
        private Gson gson;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            question_list_image = itemView.findViewById(R.id.QuestionListImage);
            question_list_name_view = itemView.findViewById(R.id.QuestionListNameTextView);
            question_list_limit_view = itemView.findViewById(R.id.QuestionListLimitTextView);
            question_list_time_view = itemView.findViewById(R.id.QuestionListTimeTextView);

            itemView.setOnClickListener(v -> {
                //TODO:判斷按下的是哪個活動,傳送對應的Event過去
                Question survey = question_list.get(getAdapterPosition());
                Bundle test = new Bundle();
                String questionjson = getGsonParser().toJson(survey);
                test.putString("question",questionjson);
                Navigation.findNavController(v).navigate(R.id.action_f5_1_to_f5_1_1,test);
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
