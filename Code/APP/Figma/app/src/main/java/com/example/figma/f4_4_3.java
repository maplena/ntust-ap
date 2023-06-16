package com.example.figma;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.figma.api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f4_4_3 extends Fragment {
    private static Gson gson;
    Event getevent;
    ImageButton cancel_back;
    Button event_name;
    Button confirm_cancel;
    EditText leave_activity_reason;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_4_3() {
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
    public static f4_4_3 newInstance(String param1, String param2) {
        f4_4_3 fragment = new f4_4_3();
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
        View view = inflater.inflate(R.layout.f4_4_3, container, false);
        // Inflate the layout for this fragment
        cancel_back = view.findViewById(R.id.CancelBackImageButton);
        cancel_back.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_4_3_to_f4_1_1));
        //TODO:看要不要抓離開原因丟給後端
        event_name = view.findViewById(R.id.WantCancelEventName);
        Bundle get = getArguments();
        getevent = getGsonParser().fromJson(get.getString("event"),Event.class);
        event_name.setText(getevent.getEvent_name());
        confirm_cancel = view.findViewById(R.id.ConfirmCancelEventButton);
        confirm_cancel.setOnClickListener(v -> {
            try {
                LeaveActivity();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Navigation.findNavController(v).navigate(R.id.action_f4_4_3_to_f4_1_1);
        });
        return view;
    }
    public static Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }

    public void LeaveActivity() throws JSONException {
            JSONObject data = new JSONObject();
            data.put("activityId",getevent.getEvent_id());
            data.put("userId",Global.studentid);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),data.toString());

            Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .LeaveActivity(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}

