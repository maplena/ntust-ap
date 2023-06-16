package com.example.figma;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f4_2_2_d2 extends Fragment {
    private static Gson gson;
    Button confirm_kick;
    Button kick_cancel;
    EditText kick_reason;


    String reason;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_2_2_d2() {
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
    public static f4_2_2_d2 newInstance(String param1, String param2) {
        f4_2_2_d2 fragment = new f4_2_2_d2();
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
        View view = inflater.inflate(R.layout.f4_2_2_d2, container, false);
        // Inflate the layout for this fragment
        Bundle get = getArguments();
        Personal getpersonal = getGsonParser().fromJson(get.getString("personal"),Personal.class);
        Event getevent = getGsonParser().fromJson(get.getString("event"),Event.class);
        kick_reason = view.findViewById(R.id.KickReason);


        confirm_kick = view.findViewById(R.id.ConfirmKick);
        confirm_kick.setOnClickListener(v -> {
            Bundle data = new Bundle();
            String personaljson = getGsonParser().toJson(getpersonal);
            String eventjson = getGsonParser().toJson(getevent);
            data.putString("personal",personaljson);
            data.putString("event",eventjson);
            data.putString("from","kick");
            data.putString("reason",kick_reason.getText().toString());
            Navigation.findNavController(v).navigate(R.id.action_f4_2_2_d2_to_f4_2_2_d3,data);
        });
        kick_cancel = view.findViewById(R.id.KickCancel);
        kick_cancel.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_2_d2_to_f4_2_2));
        return view;
    }
    public Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }


}
