package com.example.figma;

import static com.example.figma.api.RetrofitClient.BASE_URL;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.figma.api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f5_1_1 extends Fragment {
    private static Gson gson;
    ImageButton write_question_back;
    Button write_question;
    TextView surveyTitleView;
    TextView surveyDescriptionView;
    ImageView surveyImageView;
    TextView OtherPersonalTag3TextView;

    Question getQuestion;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f5_1_1() {
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
    public static f5_1_1 newInstance(String param1, String param2) {
        f5_1_1 fragment = new f5_1_1();
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
        View view = inflater.inflate(R.layout.f5_1_1, container, false);
        // Inflate the layout for this fragment
        Bundle get = getArguments();
        getQuestion = getGsonParser().fromJson(get.getString("question"),Question.class);
        write_question_back = view.findViewById(R.id.WriteQuestionBackImageButton);
        write_question_back.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_1_1_to_f5_1));
        write_question = view.findViewById(R.id.WriteQuestionButton);
        write_question.setOnClickListener(v -> {
            Uri uri = Uri.parse(getQuestion.getUrl());
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
            try {
                JoinSurvey();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
        surveyTitleView = view.findViewById(R.id.f4211tag1);
        surveyTitleView.setText(getQuestion.getTitle());
        surveyDescriptionView = view.findViewById(R.id.surveyDescription);
        surveyDescriptionView.setText(getQuestion.getDescription());
        OtherPersonalTag3TextView =view.findViewById(R.id.OtherPersonalTag3TextView);
        String limit = "人數限制: "+getQuestion.getPeopleNumLimit().toString()+"人\n截止時間: "+getQuestion.DateToSimpleString(getQuestion.getEndDate());
        OtherPersonalTag3TextView.setText(limit);
        surveyImageView = view.findViewById(R.id.surveyImageView);
        surveyImageView.setBackgroundColor(Color.WHITE);
        Picasso.with(getContext().getApplicationContext()).load(BASE_URL+"api/v1/survey/getPhoto/"+getQuestion.getId()).into(surveyImageView,new Callback() {
            @Override
            public void onSuccess() {
                // 圖片讀取完成
                Log.d("F511","取得圖片");
            }

            @Override
            public void onError() {
                // 圖片讀取失敗
            }
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

    public void JoinSurvey()throws JSONException {
        JSONObject data = new JSONObject();
        data.put("surveyId",getQuestion.getId());
        data.put("userId",Global.studentid);
        data.put("userName",Global.name);
        switch (Global.school_num){
            case 0:
                data.put("school",1);
                break;
            case 1:
                data.put("school",2);
                break;
            case 2:
                data.put("school",3);
                break;
        }
        data.put("grade",Global.userGrade);
        data.put("phone","0912345678");
        if(!Global.gender)data.put("gender",1);
        else data.put("gender",2);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),data.toString());

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .JoinSurvey(body);
        Log.d("F511","onResponse : send");

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) Log.d("F441","onResponse : receive");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}

