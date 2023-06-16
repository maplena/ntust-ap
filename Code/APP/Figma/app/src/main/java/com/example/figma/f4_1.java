package com.example.figma;


import static com.example.figma.api.RetrofitClient.BASE_URL;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.figma.Eventtest.EventItem;
import com.example.figma.api.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
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
public class f4_1 extends Fragment {
    //Button post_event;
    FloatingActionButton post_event;
    Button to_enter_event;
    Button to_event_manage;

    ImageButton event_to_notification;
    ImageButton event_to_chat;

    ImageView event_to_lottery;
    ImageView event_to_question;
    ImageView event_to_main;
    ImageView event;
    ImageView event_to_personal;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView event_list;
    EventAdapter event_adapter;
    List<Event> activity_list;


    View view;
    Integer page;
    Integer page_for_people;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_1() {
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
    public static f4_1 newInstance(String param1, String param2) {
        f4_1 fragment = new f4_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("F41", "OnCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        event_adapter = new EventAdapter(getActivity());
        activity_list = new ArrayList<>();
        page = 0;
        page_for_people = 0;
        try {
            getActivityListByLimit();
            event_adapter.setTasks(activity_list);
            //event_adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.f4_1, container, false);

        Log.d("F41", "OnCreateView");

        event_list = view.findViewById(R.id.EventList);

        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(event_list.getContext(), DividerItemDecoration.HORIZONTAL);
        mDividerItemDecoration.setDrawable(getContext().getDrawable(R.drawable.split_line3));
        DividerItemDecoration mDividerItemDecoration2 = new DividerItemDecoration(event_list.getContext(), DividerItemDecoration.VERTICAL);
        mDividerItemDecoration2.setDrawable(getContext().getDrawable(R.drawable.split_line3));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            page = 0;
            activity_list.clear();
            try {
                getActivityListByLimit();
            }catch (Exception e){
                e.printStackTrace();
            }

            //Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
        });

        event_list.addItemDecoration(mDividerItemDecoration);
        event_list.addItemDecoration(mDividerItemDecoration2);
        event_list.setLayoutManager(mLayoutManager);


        event_list.setAdapter(event_adapter);
        //event_adapter.setTasks(activity_list);


        event_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1;
                    try {
                        getActivityListByLimit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    recyclerView.post(() -> event_adapter.notifyDataSetChanged());


                    System.out.println("bottom");
                }
                /*Integer visible_item = mLayoutManager.getChildCount();
                Integer total_item = mLayoutManager.getItemCount();
                Integer past_visible_item = mLayoutManager.findFirstVisibleItemPosition();
                if(visible_item + past_visible_item >= total_item){
                    page += 1;
                    getData();
                    recyclerView.post(new Runnable() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void run() {
                            event_adapter.notifyDataSetChanged();
                        }
                    });
                }*/
            }
        });
        post_event = view.findViewById(R.id.PostEventButton);
        post_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_to_f4_2_3));
        to_enter_event = view.findViewById(R.id.ToEnterEventButton);
        to_enter_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_to_f4_1_1));
        to_event_manage = view.findViewById(R.id.ToEventManageButton);
        to_event_manage.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_to_f4_2_1));

        event_to_notification = view.findViewById(R.id.EventToNotification);
        event_to_notification.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_to_f9));
        event_to_chat = view.findViewById(R.id.EventToChat);
        event_to_chat.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_to_f8));

        event_to_lottery = view.findViewById(R.id.EventToLottery);
        event_to_lottery.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_to_f6_1));
        event_to_question = view.findViewById(R.id.EventToQuestion);
        event_to_question.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_to_f5_1));
        event_to_main = view.findViewById(R.id.EventToMain);
        event_to_main.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_to_f2_1));
        event = view.findViewById(R.id.Event);
        event_to_personal = view.findViewById(R.id.EventToPersonal);
        event_to_personal.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_to_f3_1));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("F41", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("F41", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("F41", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("F41", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("F41", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("F41", "onDestroy");
    }


    public void getActivityListByLimit() throws JSONException {
        JSONObject data = new JSONObject();
        data.put("school_limit", Global.school_num + 1);
        //android的school num    0是ntu 1是ntnu 2是ntust
        // server的school limit  0是不限 1是ntu 2是ntnu 3是ntust
        // 與server的school limit 有出入
        if (Global.gender) data.put("gender", 1);
        else data.put("gender", 2);
        data.put("grade", Global.userGrade);
        data.put("page", page);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), data.toString());

        Call<List<EventItem>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getActivityByLimit(body);
        //2022 0214 CLH 此處response物件直接改為List of EventItem (新物件，不影響Event.java) 來避免使用JSON輪詢
        call.enqueue(new Callback<List<EventItem>>() {
            @Override
            public void onResponse(Call<List<EventItem>> call, Response<List<EventItem>> response) {
                Log.d("f4_1 OnResponse", "ok");
                List<EventItem> eventItemList = new LinkedList<>();
                List<Event> Temp_list = new ArrayList<>();
                try {
                    if (response.body() != null) {
                        //s = response.body().string();
                        eventItemList = response.body();
                        Log.d("f4_1 OnResponse", "yes");

                    }
                } catch (Exception e) {
//                            e.printStackTrace();
                }
                try {
                    for (EventItem eventItem : eventItemList) {
                        Event event = new Event(eventItem);
                        Picasso.with(getContext().getApplicationContext())
                                .load(BASE_URL+"api/v1/activity/getPhoto/"+event.getEvent_id())
                                .fetch();
                        Temp_list.add(event);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                activity_list.addAll(Temp_list);
                event_adapter.setTasks(activity_list);


            }

            @Override
            public void onFailure(Call<List<EventItem>> call, Throwable t) {

            }


        });
    }


}