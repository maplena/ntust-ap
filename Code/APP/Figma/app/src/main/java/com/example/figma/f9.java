package com.example.figma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class f9 extends Fragment {
    RecyclerView notification_list;
    ImageButton notification_back;
    ImageView notification_to_lottery;
    ImageView notification_to_question;
    ImageView notification_to_main;
    ImageView notification_to_event;
    ImageView notification_to_personal;

    NotificationAdapter notification_adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f9() {
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
    public static f9 newInstance(String param1, String param2) {
        f9 fragment = new f9();
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
        View view = inflater.inflate(R.layout.f9, container, false);
        // Inflate the layout for this fragment
        notification_list = view.findViewById(R.id.NotificationRecyclerView);
        notification_adapter = new NotificationAdapter(getActivity());
        notification_list.setAdapter(notification_adapter);

        notification_back = view.findViewById(R.id.NotificationBack);
        notification_back.setOnClickListener(v -> {
            //TODO:判斷從哪進來的,就回去哪
            Navigation.findNavController(v).popBackStack();
        });

        notification_to_lottery = view.findViewById(R.id.NotificationToLottery);
        notification_to_lottery.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f9_to_f6_1));
        notification_to_question = view.findViewById(R.id.NotificationToQuestion);
        notification_to_question.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f9_to_f5_1));
        notification_to_main = view.findViewById(R.id.NotificationToMain);
        notification_to_main.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f9_to_f2_1));
        notification_to_event = view.findViewById(R.id.NotificationToEvent);
        notification_to_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f9_to_f4_1));
        notification_to_personal = view.findViewById(R.id.NotificationToPersonal);
        notification_to_personal.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f9_to_f3_1));

        return view;
    }
}