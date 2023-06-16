package com.example.figma;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.Eventtest.EventItem;
import com.example.figma.api.RetrofitClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class f2_1 extends Fragment implements AdapterView.OnItemSelectedListener {
    RecyclerView event_notification_recycler_view;
    Button show_event;
    Button show_notification;
    ImageButton main_to_notification;
    ImageButton main_to_chat;
    MainEventAdapter eventAdapter;
    NotificationAdapter notification_adapter;
    ImageView main_to_lottery;
    ImageView main_to_question;
    ImageView main;
    ImageView main_to_event;
    ImageView main_to_personal;
    private ImageSwitcher ad_imgSwc;
    private int ad_curIdx = 0;
    ImageView ad_indicator[];
    private int ad_imgSrc[];
    private static final int ad_Count = 5;
    Spinner spinner;
    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8;
    int spinner_Sel = 0;
    private float touchDownX;
    private float touchUpX;

    View view;
    List<Event> activity_list;

    private static final String links[][] = {
            {
                    "https://gpa.ntustexam.com/",
                    "https://courseselection.ntust.edu.tw/",
                    "https://querycourse.ntust.edu.tw/querycourse/#/",
                    "https://stuinfosys.ntust.edu.tw/StuScoreQueryServ/StuScoreQuery",
                    "https://2books.ntust.edu.tw/",
                    "http://media.ntust.edu.tw/map/",
                    "https://library.ntust.edu.tw/",
                    "https://dss18.ntust.edu.tw/Classroom_user/SSO_login.aspx"
            },
            {
                    "http://www.aa.ntnu.edu.tw/files/archive/2080_5d7298ff.pdf",
                    "http://www.aa.ntnu.edu.tw/course/super_pages.php?ID=0course101",
                    "http://www.aa.ntnu.edu.tw/course/super_pages.php?ID=0course101",
                    "http://www.aa.ntnu.edu.tw/records1/super_pages.php?ID=0records0",
                    "https://www.lib.ntnu.edu.tw/2books/2books.jsp",
                    "https://www.ga.ntnu.edu.tw/ntnu_map/",
                    "https://www.lib.ntnu.edu.tw/",
                    "https://ap.itc.ntnu.edu.tw/Classroom/calendar.do?mgn=0&action=show"
            },
            {
                    "https://rating.myntu.me/",
                    "https://if177.aca.ntu.edu.tw/index.php",
                    "https://nol.ntu.edu.tw/nol/coursesearch/index.php",
                    "https://www.aca.ntu.edu.tw/w/aca/CIMDService_21080415072632628",
                    "https://www.facebook.com/groups/814381758652831/",
                    "https://map.ntu.edu.tw/ntu.html?",
                    "https://www.lib.ntu.edu.tw/",
                    "https://booking.aca.ntu.edu.tw/"
            }};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f2_1() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static f2_1 newInstance(String param1, String param2) {
        f2_1 fragment = new f2_1();
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
        activity_list = new ArrayList<>();
        eventAdapter = new MainEventAdapter(getActivity());
        getNewJoinData();
        eventAdapter.setTasks(activity_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("F21","Resume");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f2_1, container, false);
        event_notification_recycler_view = view.findViewById(R.id.EventNotificationRecyclerView);
        if (!activity_list.isEmpty()) {
            activity_list.clear();
        }
        //TEST
        //2022 0215 CLH : 這邊recycler好像忘了加layout 這邊補上
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(event_notification_recycler_view.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getDrawable(R.drawable.split_line3));
        event_notification_recycler_view.addItemDecoration(dividerItemDecoration);
        event_notification_recycler_view.setLayoutManager(mLayoutManager);
        event_notification_recycler_view.setAdapter(eventAdapter);
        //2022 0215 CLH .
        notification_adapter = new NotificationAdapter(getActivity());

        ad_imgSrc = new int[]{R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4, R.drawable.ad5};
        ad_indicator = new ImageView[] {(ImageView) view.findViewById((R.id.imageView6)),
                (ImageView) view.findViewById((R.id.imageView7)),
                (ImageView) view.findViewById((R.id.imageView8)),
                (ImageView) view.findViewById((R.id.imageView9)),
                (ImageView) view.findViewById((R.id.imageView10))};

        //link objects
        ad_imgSwc = (ImageSwitcher) view.findViewById((R.id.imageSwitcher));
        ad_imgSwc.setFactory(() -> {
            ImageView imageView = new ImageView(view.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        });
        ad_imgSwc.setInAnimation(getFadeAnimation(true,300,true));
        ad_imgSwc.setOutAnimation(getFadeAnimation(false,300,true));
        ad_imgSwc.setImageResource(ad_imgSrc[ad_curIdx]);
        ad_indicator[ad_curIdx].setBackgroundResource(R.drawable.imageview_style1_on);
        ad_imgSwc.postDelayed(new Runnable() {
            int i = 0;
            public void run() {
                ad_indicator[ad_curIdx].setBackgroundResource(R.drawable.imageview_style1);
                ad_curIdx = (ad_curIdx + 1) % ad_Count;
                ad_indicator[ad_curIdx].setBackgroundResource(R.drawable.imageview_style1_on);
                ad_imgSwc.setImageResource(ad_imgSrc[ad_curIdx]);
                //Toast.makeText(view.getContext(), ad_imgSrc[ad_curIdx], Toast.LENGTH_SHORT).show();
                ad_imgSwc.postDelayed(this, 5000);
            }
        }, 1000);
        ad_imgSwc.setOnTouchListener((view, motionEvent) -> {

            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                touchDownX = motionEvent.getX();
                return true;
            }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                touchUpX = motionEvent.getX();

                if(touchDownX-touchUpX>100){
                    //往左邊滑動
                    Log.d("F21 imgSwc","left");
//                        ad_imgSwc.setInAnimation(getFadeAnimation(true,300,true));
//                        ad_imgSwc.setOutAnimation(getFadeAnimation(false,300,true));
                    ad_indicator[ad_curIdx].setBackgroundResource(R.drawable.imageview_style1);
                    ad_curIdx = (ad_curIdx + 1) % ad_Count;
                    ad_indicator[ad_curIdx].setBackgroundResource(R.drawable.imageview_style1_on);
                    ad_imgSwc.setImageResource(ad_imgSrc[ad_curIdx]);
                }else if(touchUpX-touchDownX>100) {
                    //往右邊滑動
                    Log.d("F21 imgSwc","right");
                    ad_imgSwc.setInAnimation(getFadeAnimation(true,300,false));
                    ad_imgSwc.setOutAnimation(getFadeAnimation(false,300,false));
                    ad_indicator[ad_curIdx].setBackgroundResource(R.drawable.imageview_style1);
                    ad_curIdx = (ad_curIdx + 4) % ad_Count;
                    ad_indicator[ad_curIdx].setBackgroundResource(R.drawable.imageview_style1_on);
                    ad_imgSwc.setImageResource(ad_imgSrc[ad_curIdx]);
                    ad_imgSwc.setInAnimation(getFadeAnimation(true,300,true));
                    ad_imgSwc.setOutAnimation(getFadeAnimation(false,300,true));
                }


            }
            return false;

        });



        spinner = view.findViewById(R.id.spinner1);
        iv1 = view.findViewById((R.id.OtherPersonalToLottery));
        iv2 = view.findViewById((R.id.OtherPersonalToMain));
        iv3 = view.findViewById((R.id.OtherPersonalToPersonal));
        iv4 = view.findViewById((R.id.imageView17));
        iv5 = view.findViewById((R.id.OtherPersonalToQuestion));
        iv6 = view.findViewById((R.id.OtherPersonalToEvent));
        iv7 = view.findViewById((R.id.imageView16));
        iv8 = view.findViewById((R.id.imageView18));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.college, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        int duration_time = 500;
        iv1.setOnClickListener(view -> {
            iv1.startAnimation(getScaleAnimation(300));
//                iv1.animate().rotation(360F)
//                        .setDuration(duration_time);
            gotoURL(0);
        });
        iv2.setOnClickListener(view -> {
            iv2.startAnimation(getScaleAnimation(300));
            gotoURL(1);
        });
        iv3.setOnClickListener(view -> {
            iv3.startAnimation(getScaleAnimation(300));
            gotoURL(2);
        });
        iv4.setOnClickListener(view -> {
            iv4.startAnimation(getScaleAnimation(300));
            gotoURL(3);
        });
        iv5.setOnClickListener(view -> {
            iv5.startAnimation(getScaleAnimation(300));
            gotoURL(4);
        });
        iv6.setOnClickListener(view -> {
            iv6.startAnimation(getScaleAnimation(300));
            gotoURL(5);
        });
        iv7.setOnClickListener(view -> {
            iv7.startAnimation(getScaleAnimation(300));
            gotoURL(6);
        });
        iv8.setOnClickListener(view -> {
            iv8.startAnimation(getScaleAnimation(300));
            gotoURL(7);
        });

        show_event = view.findViewById(R.id.ShowEventButton);
        show_event.setOnClickListener(v -> event_notification_recycler_view.setAdapter(eventAdapter));

        show_notification = view.findViewById(R.id.ShowNotificationButton);
        show_notification.setOnClickListener(v -> event_notification_recycler_view.setAdapter(notification_adapter));

        main_to_notification = view.findViewById(R.id.MainToNotification);
        main_to_notification.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f2_1_to_f9));

        main_to_chat = view.findViewById(R.id.MainToChat);
        main_to_chat.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f2_1_to_f8));

        main_to_lottery = view.findViewById(R.id.MainToLottery);
        main_to_lottery.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f2_1_to_f6_1));
        main_to_question = view.findViewById(R.id.MainToQuestion);
        main_to_question.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f2_1_to_f5_1));
        main = view.findViewById(R.id.Main);
        main_to_event = view.findViewById(R.id.MainToEvent);
        main_to_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f2_1_to_f4_1));

        main_to_personal = view.findViewById(R.id.MainToPersonal);
        main_to_personal.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f2_1_to_f3_1));

        return view;
    }
    private void gotoURL(int idx) {
        Uri uri = Uri.parse(links[spinner_Sel][idx]);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinner_Sel = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getNewJoinData() {
        Call<List<EventItem>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getJoinActivity(Global.studentid, 0);
        Log.d("f2_1", "getNewJoinData : send");
        call.enqueue(new Callback<List<EventItem>>() {
            @Override
            public void onResponse(Call<List<EventItem>> call, Response<List<EventItem>> response) {
                Log.d("f2_1 OnResponse", "getNewJoinData : received");
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
                eventAdapter.setTasks(Temp_list);


            }

            @Override
            public void onFailure(Call<List<EventItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private Animation getScaleAnimation(long durationMillis) {
        Animation a = AnimationUtils.loadAnimation(this.getContext(),R.anim.scale);
        a.setDuration(durationMillis);
        return a;
    }


    private Animation getFadeAnimation(boolean in, long durationMillis,boolean direct) {
        Animation a;
        if(direct){
            a = AnimationUtils.loadAnimation(this.getContext(), in ? R.anim.slide_in_left : R.anim.slide_out_left);
        }else {
            a = AnimationUtils.loadAnimation(this.getContext(), in ? R.anim.slide_in_right : R.anim.slide_out_right);
        }
        a.setDuration(durationMillis);
        return a;
    }
}