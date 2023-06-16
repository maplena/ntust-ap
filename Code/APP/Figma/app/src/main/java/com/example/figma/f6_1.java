package com.example.figma;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.api.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f6_1 extends Fragment {
    Button to_lottery_list;
    Button to_exchange_list;
    View current_page_line;
    ImageView lottery;
    ImageView lottery_to_question;
    ImageView lottery_to_main;
    ImageView lottery_to_event;
    ImageView lottery_to_personal;
    RecyclerView lottery_list_recyclerview;
    LotteryListAdapter lottery_list_adapter;
    ExchangeListAdapter exchange_list_adapter;

    Integer page = 0;
    Integer now = 0; //0 = 抽獎頁,1 = 兌換頁
    List<Lottery> lottery_list;
    List<Ticket> ticket_list;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f6_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static f6_1 newInstance(String param1, String param2) {
        f6_1 fragment = new f6_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f6_1, container, false);
        page = 0;
        now = 0;
        lottery_list = new ArrayList<>();
        ticket_list = new ArrayList<>();

        try {
            getLotteryListByLimit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        lottery_list_recyclerview = view.findViewById(R.id.LotteryListRecyclerView);


        lottery_list_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(lottery_list_recyclerview.getContext(), DividerItemDecoration.VERTICAL);
        mDividerItemDecoration.setDrawable(getContext().getDrawable(R.drawable.split_line3));
        lottery_list_recyclerview.addItemDecoration(mDividerItemDecoration);

        lottery_list_adapter = new LotteryListAdapter(getActivity());
        exchange_list_adapter = new ExchangeListAdapter(getActivity());
        lottery_list_recyclerview.setAdapter(lottery_list_adapter);

        lottery_list_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1;
                    if(now == 0){
                        try {
                            getLotteryListByLimit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        recyclerView.post(() -> lottery_list_adapter.notifyDataSetChanged());
                    }
                    else {
                        try {
                            getMyLottery();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        recyclerView.post(() -> exchange_list_adapter.notifyDataSetChanged());
                    }

                }
            }
        });

        current_page_line = view.findViewById(R.id.f61CurrentPageLine);
        to_lottery_list = view.findViewById(R.id.ChangeToLotteryListButton);
        to_lottery_list.setOnClickListener(v -> {
            to_lottery_list.setTextColor(Color.rgb(223, 136, 255));
            to_exchange_list.setTextColor(Color.rgb(198, 198, 198));
            current_page_line.setX(0);
            now = 0;
            page = 0;
            try {
                getLotteryListByLimit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            lottery_list_recyclerview.setAdapter(lottery_list_adapter);

        });

        to_exchange_list = view.findViewById(R.id.ChangeToExchangeButton);
        to_exchange_list.setOnClickListener(v -> {
            to_exchange_list.setTextColor(Color.rgb(223, 136, 255));
            to_lottery_list.setTextColor(Color.rgb(198, 198, 198));
            current_page_line.setX(current_page_line.getWidth());
            now = 1;
            page = 0;
            try {
                getMyLottery();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            lottery_list_recyclerview.setAdapter(exchange_list_adapter);

        });

        lottery = view.findViewById(R.id.Lottery);
        lottery_to_question = view.findViewById(R.id.LotteryToQuestion);
        lottery_to_question.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f6_1_to_f5_1));

        lottery_to_main = view.findViewById(R.id.LotteryToMain);
        lottery_to_main.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f6_1_to_f2_1));
        lottery_to_event = view.findViewById(R.id.LotteryToEvent);
        lottery_to_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f6_1_to_f4_1));
        lottery_to_personal = view.findViewById(R.id.LotteryToPersonal);
        lottery_to_personal.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f6_1_to_f3_1));
        return view;
    }

    public void getLotteryListByLimit() throws JSONException {
        JSONObject data = new JSONObject();
        data.put("page",page);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),data.toString());

        Call<List<Lottery>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getLottery(body);
        //2022 0214 CLH 此處response物件直接改為List of EventItem (新物件，不影響Event.java) 來避免使用JSON輪詢
        call.enqueue(new Callback<List<Lottery>>() {
            @Override
            public void onResponse(Call<List<Lottery>> call, Response<List<Lottery>> response) {
                List<Lottery> lotteryItemList = new ArrayList<>();
                //List<Question> Temp_list = new ArrayList<>();
                try {
                    if (response.body() != null) {
                        //s = response.body().string();
                        lotteryItemList = response.body();

                    }
                } catch (Exception e) {
//                            e.printStackTrace();
                }
                lottery_list.addAll(lotteryItemList);
                lottery_list_adapter.setTasks(lottery_list);

            }

            @Override
            public void onFailure(Call<List<Lottery>> call, Throwable t) {
                t.printStackTrace();
            }


        });
    }

    public void getMyLottery() throws JSONException {

        Call<List<Ticket>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getMyLottery(Global.studentid,page);
        //2022 0214 CLH 此處response物件直接改為List of EventItem (新物件，不影響Event.java) 來避免使用JSON輪詢
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                List<Ticket> ticketItemList = new ArrayList<>();
                //List<Question> Temp_list = new ArrayList<>();
                try {
                    if (response.body() != null) {
                        //s = response.body().string();
                        ticketItemList = response.body();

                    }
                } catch (Exception e) {
//                            e.printStackTrace();
                }
                ticket_list.addAll(ticketItemList);
                exchange_list_adapter.setTasks(ticket_list);
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                t.printStackTrace();
            }


        });
    }
}

