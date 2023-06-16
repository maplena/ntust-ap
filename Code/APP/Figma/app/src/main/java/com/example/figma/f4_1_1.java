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
public class f4_1_1 extends Fragment {
    Button enter_event_back_event;
    Button enter_event_to_manage;
    Button change_to_un_start_event;
    Button change_to_end_event;

    ImageButton enter_event_to_notification;
    ImageButton enter_event_to_chat;

    ImageView enter_event_to_lottery;
    ImageView enter_event_to_question;
    ImageView enter_event_to_main;
    ImageView enter_event_to_event;
    ImageView enter_event_to_personal;

    RecyclerView enter_event_list;
    UnStartEventAdapter un_start_event_adapter;
    EndEventAdapter end_event_adapter;

    List<Event> activity_list;
    List<Event> past_activity_list;

    View view;

    Integer page=0;
    Integer page_for_people = 0;
    Integer now=0;
    Integer now_people = 0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_1_1() {
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
    public static f4_1_1 newInstance(String param1, String param2) {
        f4_1_1 fragment = new f4_1_1();
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

        un_start_event_adapter = new UnStartEventAdapter(getActivity());
        end_event_adapter = new EndEventAdapter(getActivity());
        activity_list = new ArrayList<>();
        past_activity_list = new ArrayList<>();
        getNewJoinData();
        getNewPastData();
        un_start_event_adapter.setTasks(activity_list);
        end_event_adapter.setTasks(past_activity_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //--- 01_24 boyan 封存 ---
        // 'getJoinActivity(java.lang.String)' in 'com.example.figma.api.API' cannot be applied to '()'
        Log.d("F411", "OnCreateView");
        view = inflater.inflate(R.layout.f4_1_1, container, false);
        // Inflate the layout for this fragment
        if (!activity_list.isEmpty()) {
            activity_list.clear();
        }
        if (!past_activity_list.isEmpty()) {
            past_activity_list.clear();
        }

        enter_event_list = view.findViewById(R.id.EnterEventListRecyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(enter_event_list.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getDrawable(R.drawable.split_line2));
        enter_event_list.addItemDecoration(dividerItemDecoration);
        enter_event_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        enter_event_list.setAdapter(un_start_event_adapter);

        enter_event_back_event = view.findViewById(R.id.EnterEventToEventButton);
        enter_event_to_manage = view.findViewById(R.id.EnterEventToEventManageButton);
        change_to_un_start_event = view.findViewById(R.id.ChangeToUnStartListButton);

        change_to_end_event = view.findViewById(R.id.ChangeToEndListButton);
        enter_event_to_notification = view.findViewById(R.id.EnterEventToNotification);
        enter_event_to_chat = view.findViewById(R.id.EnterEventToChat);
        enter_event_to_lottery = view.findViewById(R.id.EnterEventToLottery);
        enter_event_to_question = view.findViewById(R.id.EnterEventToQuestion);
        enter_event_to_main = view.findViewById(R.id.EnterEventToMain);
        enter_event_to_event = view.findViewById(R.id.EnterEventToEvent);
        enter_event_to_personal = view.findViewById(R.id.EnterEventToPersonal);


        enter_event_back_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f4_1));

        enter_event_to_manage.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f4_2_1));

        enter_event_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    if (now == 0) {
                        page += 1;
                        getNewJoinData();
                        recyclerView.post(() -> un_start_event_adapter.notifyDataSetChanged());

                    } else {
                        page += 1;
                        getNewPastData();
                        recyclerView.post(() -> end_event_adapter.notifyDataSetChanged());

                    }
                }
            }
        });


        change_to_un_start_event.setOnClickListener(v -> {
            page = 0;
            enter_event_list.setAdapter(un_start_event_adapter);
            if (!activity_list.isEmpty()) {
                activity_list.clear();
            }
            getNewJoinData();

            now = 0;
            change_to_end_event.setTextColor(Color.rgb(198, 198, 198));
            //change_to_end_event.setBackgroundColor(Color.rgb(240,240,240));

            change_to_un_start_event.setTextColor(Color.rgb(255, 187, 104));
            //change_to_un_start_event.setBackgroundColor(Color.WHITE);
        });

        change_to_end_event.setOnClickListener(v -> {
            page = 0;
            if (past_activity_list != null) past_activity_list.clear();
            getNewPastData();
            enter_event_list.setAdapter(end_event_adapter);

            now = 1;
            change_to_un_start_event.setTextColor(Color.rgb(198, 198, 198));
            //change_to_un_start_event.setBackgroundColor(Color.rgb(240,240,240));

            change_to_end_event.setTextColor(Color.rgb(255, 187, 104));
            //change_to_end_event.setBackgroundColor(Color.WHITE);
        });


        enter_event_to_notification.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f9));

        enter_event_to_chat.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f8));


        enter_event_to_lottery.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f6_1));

        enter_event_to_question.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f5_1));

        enter_event_to_main.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f2_1));

        enter_event_to_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f4_1));

        enter_event_to_personal.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_1_to_f3_1));


        return view;
    }


    public void getNewJoinData() {
        Call<List<EventItem>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getJoinActivity(Global.studentid, page);
        Log.d("f4_1_1", "getNewJoinData : send");
        call.enqueue(new Callback<List<EventItem>>() {
            @Override
            public void onResponse(Call<List<EventItem>> call, Response<List<EventItem>> response) {
                Log.d("f4_1_1 OnResponse", "getNewJoinData : received");
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
                activity_list.addAll(Temp_list);
                //un_start_event_adapter.notifyDataSetChanged();
                un_start_event_adapter.setTasks(Temp_list);


            }

            @Override
            public void onFailure(Call<List<EventItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getNewPastData() {

        Call<List<EventItem>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getPastActivity(Global.studentid, page);
        Log.d("f4_1_1", "getNewPastData : send");
        call.enqueue(new Callback<List<EventItem>>() {
            @Override
            public void onResponse(Call<List<EventItem>> call, Response<List<EventItem>> response) {
                Log.d("f4_1_1", "getNewPastData : receive");
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
                past_activity_list.addAll(Temp_list);
                end_event_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<EventItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
//
//    public void getNowJoin(Integer aid) {
//
//        Call<List<ParticipentItem>> call = RetrofitClient
//                .getInstance()
//                .getAPI()
//                .getActivityMember(aid, page);
//
//        call.enqueue(new Callback<List<ParticipentItem>>() {
//            @Override
//            public void onResponse(Call<List<ParticipentItem>> call, Response<List<ParticipentItem>> response) {
//                List<ParticipentItem> participentItemList = new LinkedList<>();
//                try {
//                    if (response.body() != null) {
//                        participentItemList = response.body();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                try {
//                    now_people = now_people + participentItemList.size();
//
////                    getActivity().runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            if(now_people!=0){
////                                un_start_event_adapter.notifyDataSetChanged();
////                                end_event_adapter.notifyDataSetChanged();
////                            }
////
////                        }
////                    });
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ParticipentItem>> call, Throwable t) {
//
//            }
//
//
//        });
//
//
//    }
}