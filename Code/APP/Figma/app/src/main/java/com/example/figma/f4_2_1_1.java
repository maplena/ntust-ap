package com.example.figma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f4_2_1_1 extends Fragment {
    private static Gson gson;
    Button confirm_cancel;
    Button cancel_delete;

    TextView f4211pagetitle;
    TextView OtherPersonalTag3TextView;
    TextView f4211tag1;
    EditText cancel_reason;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_2_1_1() {
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
    public static f4_2_1_1 newInstance(String param1, String param2) {
        f4_2_1_1 fragment = new f4_2_1_1();
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
        View view = inflater.inflate(R.layout.f4_2_1_1, container, false);
        // Inflate the layout for this fragment
        Bundle get = getArguments();
        Event getevent = getGsonParser().fromJson(get.getString("event"),Event.class);

        cancel_reason = view.findViewById(R.id.CancelReasonEditText);
        f4211pagetitle = view.findViewById(R.id.f4211pagetitle);
        f4211pagetitle.setText(getevent.getEvent_name());
        OtherPersonalTag3TextView = view.findViewById(R.id.OtherPersonalTag3TextView);
        OtherPersonalTag3TextView.setText(getevent.getEvent_host());
        f4211tag1 = view.findViewById(R.id.f4211tag1);
        f4211tag1.setText(getevent.getEvent_tag());
        confirm_cancel = view.findViewById(R.id.ConfirmCancelButton);
        confirm_cancel.setOnClickListener(v -> {
            Bundle data = new Bundle();
            String eventjson = getGsonParser().toJson(getevent);
            data.putString("event",eventjson);
            data.putString("reason",cancel_reason.getText().toString());
            data.putString("from","activity");
            Navigation.findNavController(v).navigate(R.id.action_f4_2_1_1_to_f4_2_2_d3,data);
        });
        cancel_delete = view.findViewById(R.id.CancelDeleteButton);
        cancel_delete.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_1_1_to_f4_2_1));
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
