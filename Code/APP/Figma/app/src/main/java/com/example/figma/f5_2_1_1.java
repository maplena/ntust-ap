package com.example.figma;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
public class f5_2_1_1 extends Fragment {
    private static Gson gson;
    Button confirm_cancel;
    Button cancel_delete;
    Question question;
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

    public f5_2_1_1() {
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
    public static f5_2_1_1 newInstance(String param1, String param2) {
        f5_2_1_1 fragment = new f5_2_1_1();
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
        View view = inflater.inflate(R.layout.f5_2_1_1, container, false);
        // Inflate the layout for this fragment
        Bundle get = getArguments();
        question = getGsonParser().fromJson(get.getString("question"),Question.class);

        cancel_reason = view.findViewById(R.id.CancelReasonEditText);
        f4211pagetitle = view.findViewById(R.id.f4211pagetitle);
        f4211pagetitle.setText(question.getTitle());
        OtherPersonalTag3TextView = view.findViewById(R.id.OtherPersonalTag3TextView);
        OtherPersonalTag3TextView.setText(question.getHost());
        f4211tag1 = view.findViewById(R.id.f4211tag1);
        f4211tag1.setText(question.getTag());
        confirm_cancel = view.findViewById(R.id.ConfirmCancelButton);
        confirm_cancel.setOnClickListener(v -> {
            try {
                CancelSurvey();
            }catch (Exception exception){
                exception.printStackTrace();
            }
            Navigation.findNavController(v).navigate(R.id.action_f5_2_1_1_to_f5_2);
        });
        cancel_delete = view.findViewById(R.id.CancelDeleteButton);
        cancel_delete.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_2_1_1_to_f5_2));
        return view;
    }

    public Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }
    public void CancelSurvey() throws JSONException {
        JSONObject data = new JSONObject();
        data.put("surveyId",question.getId());
        data.put("requestUserId",Global.studentid);
        data.put("reason",cancel_reason.getText().toString());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),data.toString());
        Log.d("Cancel Survey data","0" + data.toString());
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .CancelSurvey(body);

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
