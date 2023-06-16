package com.example.figma;

import static com.example.figma.api.RetrofitClient.BASE_URL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f4_5 extends Fragment {
    private static Gson gson;
    Button sign_up;
    Button people_list;
    Button cancel_btn;
    TextView event_detail_name;
    TextView event_detail_tag1;
    TextView event_detail_tag2;
    TextView event_detail_creator;
    TextView event_detail_date;
    TextView event_detail_position;
    TextView event_detail_people;
    ImageView event_image_view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_5() {
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
    public static f4_5 newInstance(String param1, String param2) {
        f4_5 fragment = new f4_5();
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
        View view = inflater.inflate(R.layout.f4_5, container, false);
        // Inflate the layout for this fragment
        event_detail_name = view.findViewById(R.id.EventDetailNameTextView);
        event_detail_tag1 = view.findViewById(R.id.EventDetailTag1TextView);
        event_detail_tag2 = view.findViewById(R.id.EventDetailTag2TextView);
        event_detail_date = view.findViewById(R.id.EventDetailDateTextView);
        event_detail_creator = view.findViewById(R.id.EventDetailCreatorTextView);
        event_detail_position = view.findViewById(R.id.EventDetailPositionTextView);
        event_detail_people = view.findViewById(R.id.EventDetailPeopleTextView);
        event_image_view = view.findViewById(R.id.EventListImageView);

        Bundle get = getArguments();
        Event getevent = getGsonParser().fromJson(get.getString("event"),Event.class);
        event_detail_name.setText(getevent.getEvent_name());
        event_detail_tag1.setText("#" + getevent.getEvent_tag());
        //event_detail_tag2.setText("#" + getevent.getEvent_tag()[1]);
        event_detail_date.setText(getevent.DateToSimpleString(getevent.getEvent_start_date()));
        event_detail_position.setText(getevent.getEvent_place());
        event_detail_creator.setText(getevent.getEvent_host());
        event_detail_people.setText("參與人數: " + getevent.getNow_people() + "/" + getevent.getMax_people());
        Picasso.with(this.getContext()).load(BASE_URL+"api/v1/activity/getPhoto/"+getevent.getEvent_id()).into(event_image_view);



        sign_up = view.findViewById(R.id.F45SignUpButton);
        sign_up.setOnClickListener(v -> {
                Bundle data = new Bundle();
                String eventjson = getGsonParser().toJson(getevent);
                data.putString("event",eventjson);
                Navigation.findNavController(v).navigate(R.id.action_f4_5_to_f4_4_1,data);
        });

        cancel_btn = view.findViewById(R.id.F45CancelButton);
        cancel_btn.setOnClickListener(v -> {
            Bundle data = new Bundle();
            String eventjson = getGsonParser().toJson(getevent);
            data.putString("event",eventjson);
            Navigation.findNavController(v).navigate(R.id.action_f4_5_to_f4_4_3,data);
        });

        people_list = view.findViewById(R.id.EventPeopleListButton);
        people_list.setOnClickListener(v -> {
            Bundle event_data = new Bundle();
            String eventjson = getGsonParser().toJson(getevent);
            event_data.putString("event",eventjson);
            event_data.putString("enter","eventlist");
            Navigation.findNavController(v).navigate(R.id.action_f4_5_to_f4_5_1,event_data);
        });

        String po = get.getString("enter");
        if(po.equals("eventlist")){
            cancel_btn.setVisibility(View.INVISIBLE);
            cancel_btn.setEnabled(false);
            people_list.setVisibility(View.INVISIBLE);
            people_list.setEnabled(false);
        }
        else{
            sign_up.setVisibility(View.INVISIBLE);
            sign_up.setEnabled(false);
        }

        return view;
    }

    public static Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }
}
