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

public class ExchangeListAdapter extends RecyclerView.Adapter<ExchangeListAdapter.TaskViewHolder>{

    // Class variables for the List that holds task data and the Context
    private List<Ticket> ticket_list;
    private Context mContext;
    public Ticket exchange;



    public ExchangeListAdapter(Context context) {
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
                .inflate(R.layout.f6_2_rec_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(ExchangeListAdapter.TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        exchange = ticket_list.get(position);
//        String lottery_name = exchange.getLottery_name();
//        String lottery_num = exchange.getLottery_num();
//        String lottery_use_time = exchange.DateToString(exchange.getLottery_use_time());
        //Set values
        holder.exchange_list_name_view.setText(exchange.getTitle());
        holder.exchange_list_use_time_view.setText("使用期限: " + exchange.getEndDate());

    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (ticket_list == null) {
            return 0;
        }
        return ticket_list.size();
    }

    public List<Ticket> getTasks() {
        return ticket_list;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Ticket> taskEntries) {
        ticket_list = taskEntries;
        notifyDataSetChanged();
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder{

        // Class variables for the task description and priority TextViews
        TextView exchange_list_name_view;
        TextView exchange_list_num_view;
        TextView exchange_list_use_time_view;

        Button exchange_button;
        Button exchange_rule;
        private Gson gson;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            exchange_list_name_view = itemView.findViewById(R.id.ExchangeNameTextView);
            exchange_list_use_time_view = itemView.findViewById(R.id.ExchangeUseTimeTextView);


            exchange_button = itemView.findViewById(R.id.ExchangeButton);
            exchange_button.setOnClickListener(v -> {
                Bundle data = new Bundle();
                String ticketjson = getGsonParser().toJson(exchange);
                data.putString("exchange",ticketjson);
                data.putString("from","exchangelist");
                Navigation.findNavController(v).navigate(R.id.action_f6_1_to_f4_2_2_d3,data);
            });

            exchange_rule = itemView.findViewById(R.id.ExchangeRuleButton);
            exchange_rule.setOnClickListener(v -> {
                Bundle data = new Bundle();
                data.putString("from","exchangelist");
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
}
