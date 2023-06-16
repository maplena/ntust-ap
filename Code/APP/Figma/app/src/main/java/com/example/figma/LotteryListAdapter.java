package com.example.figma;

import static com.example.figma.api.RetrofitClient.BASE_URL;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LotteryListAdapter extends RecyclerView.Adapter<LotteryListAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Lottery> lottery_list;
    private Context mContext;
    public Lottery lottery;



    public LotteryListAdapter(Context context) {
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
                .inflate(R.layout.f6_1_rec_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(LotteryListAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        lottery = lottery_list.get(position);
        String lottery_name = lottery.getTitle();
        Integer lottery_num = lottery.getLeftAmount();
        String lottery_use_time = lottery.getEndDate();
        Double lottery_probability = lottery.getProbability() * 100;
        Picasso.with(mContext.getApplicationContext())
                .load(BASE_URL+"api/v1/lottery/getPhoto/"+lottery.getId())
                .fetch();
        //Set values
        holder.lottery_list_name_view.setText(lottery_name);
        holder.lottery_list_num_view.setText("剩餘張數: " + lottery_num);
        holder.lottery_list_use_time_view.setText(lottery_use_time);
        holder.lottery_list_probability_view.setText("機率: " + lottery_probability.toString() + "%");
        Picasso.with(mContext.getApplicationContext())
                .load(BASE_URL+"api/v1/lottery/getPhoto/"+lottery.getId())
                .fit()
                .centerCrop()
                .into(holder.lottery_list_image);
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (lottery_list == null) {
            return 0;
        }
        return lottery_list.size();
    }

    public List<Lottery> getTasks() {
        return lottery_list;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Lottery> taskEntries) {
        lottery_list = taskEntries;
        notifyDataSetChanged();
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder{

        // Class variables for the task description and priority TextViews
        ImageView lottery_list_image;
        TextView lottery_list_name_view;
        TextView lottery_list_num_view;
        TextView lottery_list_use_time_view;
        TextView lottery_list_probability_view;
        TextView lottery_list_cooldown_view;

        Button lottery_button;
        Button lottery_rule;
        private Gson gson;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            lottery_list_image = itemView.findViewById(R.id.LotteryListImageView);
            lottery_list_name_view = itemView.findViewById(R.id.LotteryListNameTextView);
            lottery_list_num_view = itemView.findViewById(R.id.LotteryListNumTextView);
            lottery_list_use_time_view = itemView.findViewById(R.id.LotteryListUseTimeTextView);
            lottery_list_probability_view = itemView.findViewById(R.id.LotteryListProbabilityView);
            lottery_list_cooldown_view = itemView.findViewById(R.id.LotteryListCoolDownTextView);

            lottery_button = itemView.findViewById(R.id.LotteryButton);
            lottery_button.setOnClickListener(v -> {
                try {
                    draw(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Lottery List Adapter","失敗");
                }
            });

            lottery_rule = itemView.findViewById(R.id.LotteryRuleButton);
            lottery_rule.setOnClickListener(v -> {
                Bundle data = new Bundle();
                data.putString("from","lotterylist");
                Navigation.findNavController(v).navigate(R.id.action_f6_1_to_f4_2_2_d3,data);
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
    public void draw(View v) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("lotteryId", lottery.getId());
        data.put("userId", Global.studentid);
//        RequestBody body =
//                RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), data.toString());
        RequestBody body =
                RequestBody.create(MediaType.parse("application/json"), data.toString());

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .LotteryDraw(body);
        Log.d("Lottert List Adapter 抽獎","執行中3");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = "";
                try {
                    if (response.body()!=null)s = response.body().string();
                } catch (IOException e) {
                    System.out.println("Get response.body Fail");
                }

                switch (s){
                    case "成功，恭喜抽中":
                        Bundle data = new Bundle();
                        data.putString("from","lotterylist");
                        data.putString("get","yes");
                        Navigation.findNavController(v).navigate(R.id.action_f6_1_to_f4_2_2_d3,data);
                        break;
                    case "成功，沒有抽中":
                        Bundle datan = new Bundle();
                        datan.putString("from","lotterylist");
                        datan.putString("get","no");
                        Navigation.findNavController(v).navigate(R.id.action_f6_1_to_f4_2_2_d3,datan);
                        break;
                    case "失敗，該抽獎已結束":
                        Bundle datas = new Bundle();
                        datas.putString("from","lotterylist");
                        datas.putString("get","nosuccess");
                        Navigation.findNavController(v).navigate(R.id.action_f6_1_to_f4_2_2_d3,datas);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.d("Lottert List Adapter 抽獎","執行失敗");
            }


        });
    }

}

