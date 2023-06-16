package com.example.figma;

import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.Eventtest.EventItem;
import com.example.figma.api.RetrofitClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f4_2_1 extends Fragment {
    Button event_manage_back_to_event;
    Button event_manage_to_enter_event;
    //Button manage_to_post;
    Button change_to_post;
    Button change_to_post_end;

    ImageButton event_manage_to_notification;
    ImageButton event_manage_to_chat;

    ImageView event_manage_to_lottery;
    ImageView event_manage_to_question;
    ImageView event_manage_to_main;
    ImageView event_manage_to_event;
    ImageView event_manage_to_personal;

    RecyclerView event_manage;
    PostEventAdapter post_event_adapter;
    EndPostEventAdapter end_post_event_adapter;
    List<Event> post_activity_list;
    List<Event> post_end_activity_list;

    Integer page;
    Integer page2;
    Integer page_for_people = 0;
    Integer now = 0; //0 = 自己發布的活動 1 = 自己發布的已經結束的活動
    Integer now_people = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_2_1() {
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
    public static f4_2_1 newInstance(String param1, String param2) {
        f4_2_1 fragment = new f4_2_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("F421", "OnCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        post_event_adapter = new PostEventAdapter(getActivity());
        end_post_event_adapter = new EndPostEventAdapter(getActivity());
        post_activity_list = new ArrayList<>();
        post_end_activity_list = new ArrayList<>();
        page = 0;
        page2 = 0;
        getPostData();
        getEndData();
        post_event_adapter.setTasks(post_activity_list);
        end_post_event_adapter.setTasks(post_end_activity_list);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f4_2_1, container, false);
        Log.d("F421", "OnCreateView");
        // Inflate the layout for this fragment

//        if(post_activity_list != null)post_activity_list.clear();
//        if(post_end_activity_list != null)post_end_activity_list.clear();
        event_manage = view.findViewById(R.id.EventManageRecyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(event_manage.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getDrawable(R.drawable.split_line2));
        event_manage.addItemDecoration(dividerItemDecoration);
        event_manage.setLayoutManager(new LinearLayoutManager(getActivity()));

        event_manage.setAdapter(post_event_adapter);
        post_event_adapter.setTasks(post_activity_list);


        change_to_post = view.findViewById(R.id.ChangeToPostListButton);
        change_to_post.setOnClickListener(v -> {
//                page = 0;
            now = 0;
            event_manage.setAdapter(post_event_adapter);
            change_to_post.setTextColor(Color.rgb(255, 187, 104));
            change_to_post_end.setTextColor(Color.rgb(198, 198, 198));
        });

        change_to_post_end = view.findViewById(R.id.ChangeToPostEndListButton);
        change_to_post_end.setOnClickListener(v -> {
//                page2 = 0;
            now = 1;
            event_manage.setAdapter(end_post_event_adapter);
            change_to_post_end.setTextColor(Color.rgb(255, 187, 104));
            change_to_post.setTextColor(Color.rgb(198, 198, 198));
        });
        event_manage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {

                    if (now == 0) {
                        page += 1;
                        getPostData();
                        recyclerView.post(() -> post_event_adapter.notifyDataSetChanged());
                    } else {
                        page2 += 1;
                        getEndData();
                        recyclerView.post(() -> end_post_event_adapter.notifyDataSetChanged());
                    }
                }
            }
        });

        event_manage_back_to_event = view.findViewById(R.id.EventManageToEventButton);
        event_manage_back_to_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f4_1));
        event_manage_to_enter_event = view.findViewById(R.id.EventManageToEnterEventButton);
        event_manage_to_enter_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f4_1_1));

        event_manage_to_notification = view.findViewById(R.id.EventManageToNotification);
        event_manage_to_notification.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f9));
        event_manage_to_chat = view.findViewById(R.id.EventManageToChat);
        event_manage_to_chat.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f8));

//        manage_to_post = view.findViewById(R.id.ManagePostEventButton);
//        manage_to_post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f4_2_3);
//            }
//        });
        event_manage_to_lottery = view.findViewById(R.id.EventManageToLottery);
        event_manage_to_lottery.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f6_1));
        event_manage_to_question = view.findViewById(R.id.EventManageToQuestion);
        event_manage_to_question.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f5_1));
        event_manage_to_main = view.findViewById(R.id.EventManageToMain);
        event_manage_to_main.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f2_1));
        event_manage_to_event = view.findViewById(R.id.EventManageToEvent);
        event_manage_to_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f4_1));
        event_manage_to_personal = view.findViewById(R.id.EventManageToPersonal);
        event_manage_to_personal.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_1_to_f3_1));
        return view;
    }


    public void getPostData() {
        Call<List<EventItem>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getActivityByPromoter(Global.studentid, "running", page);
        Log.d("f4_2_1", "getPostData : send");
        call.enqueue(new Callback<List<EventItem>>() {
            @Override
            public void onResponse(Call<List<EventItem>> call, Response<List<EventItem>> response) {
                Log.d("f4_2_1", "getPostData : receive");
                List<EventItem> eventItemList = new LinkedList<>();
                List<Event> Temp_list = new ArrayList<>();
                try {
                    if (response.body() != null) {
                        eventItemList = response.body();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    for (EventItem eventItem : eventItemList) {
                        Event event = new Event(eventItem);
                        Temp_list.add(event);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                post_activity_list.addAll(Temp_list);
                post_event_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<EventItem>> call, Throwable t) {

            }
        });

    }

    public void getEndData() {
        Call<List<EventItem>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getActivityByPromoter(Global.studentid, "end", page2);
        Log.d("f4_2_1", "getEndData : send");
        call.enqueue(new Callback<List<EventItem>>() {
            @Override
            public void onResponse(Call<List<EventItem>> call, Response<List<EventItem>> response) {
                Log.d("f4_2_1", "getEndData : receive");
                List<EventItem> eventItemList = new LinkedList<>();
                List<Event> Temp_list = new ArrayList<>();
                try {
                    if (response.body() != null) {
                        eventItemList = response.body();
                    }
                } catch (Exception e) {

//                            e.printStackTrace();
                }
                try {
                    for (EventItem eventItem : eventItemList) {
                        Event event = new Event(eventItem);
                        Temp_list.add(event);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                post_end_activity_list.addAll(Temp_list);
                end_post_event_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<EventItem>> call, Throwable t) {

            }
        });

    }


}