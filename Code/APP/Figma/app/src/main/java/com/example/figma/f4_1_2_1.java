package com.example.figma;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
public class f4_1_2_1 extends Fragment {
    Button confirm_send_score;
    Button cancel_send_score;

    TextView score_event_name;
    TextView score_event_tag1;
    TextView score_event_tag2;
    TextView score_event_creator;

    ImageButton first_star;
    ImageButton second_star;
    ImageButton third_star;
    ImageButton fourth_star;
    ImageButton fifth_star;

    EditText score_content;

    Integer rating;
    View view;
    Event getevent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_1_2_1() {
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
    public static f4_1_2_1 newInstance(String param1, String param2) {
        f4_1_2_1 fragment = new f4_1_2_1();
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
        view = inflater.inflate(R.layout.f4_1_2_1, container, false);
        // Inflate the layout for this fragment
        initialization();
        Bundle get = getArguments();
        getevent = getGsonParser().fromJson(get.getString("event"),Event.class);
        score_event_name.setText(getevent.getEvent_name());
        score_event_tag1.setText(getevent.getEvent_tag());
        //score_event_tag2.setText(getevent.getEvent_tag()[1]);
        score_event_creator.setText(getevent.getEvent_host());

        confirm_send_score.setOnClickListener(v -> {

            try {
                SendScore();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Navigation.findNavController(v).navigate(R.id.action_f4_1_2_1_to_f4_1_1);
        });


        cancel_send_score.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_1_2_1_to_f4_1_1));

        return view;
    }


    private static Gson gson;

    public static Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }

    public void initialization(){
        score_event_name = view.findViewById(R.id.ScoreEventNameTextView);
        score_event_tag1 = view.findViewById(R.id.ScoreEventTag1TextView);
        score_event_tag2 = view.findViewById(R.id.ScoreEventTag2TextView);
        score_event_creator = view.findViewById(R.id.ScoreEventCreatorTextView);
        confirm_send_score = view.findViewById(R.id.ConfirmSendScoreButton);
        cancel_send_score = view.findViewById(R.id.CancelSendScoreButton);
        score_content = view.findViewById(R.id.ScoreContent);
        first_star = view.findViewById(R.id.ScoreFirstStar);
        second_star = view.findViewById(R.id.ScoreSecondStar);
        third_star = view.findViewById(R.id.ScoreThirdStar);
        fourth_star = view.findViewById(R.id.ScoreFourthStar);
        fifth_star = view.findViewById(R.id.ScoreFifthStar);
        first_star.setOnClickListener(this::Score);
        second_star.setOnClickListener(this::Score);
        third_star.setOnClickListener(this::Score);
        fourth_star.setOnClickListener(this::Score);
        fifth_star.setOnClickListener(this::Score);
        first_star.setImageResource(R.drawable.bi_star_fill__1_);
        second_star.setImageResource(R.drawable.star_unfilled);
        third_star.setImageResource(R.drawable.star_unfilled);
        fourth_star.setImageResource(R.drawable.star_unfilled);
        fifth_star.setImageResource(R.drawable.star_unfilled);
        rating = 1;
    }

    public void Score(View v){
        switch (v.getId()){
            case R.id.ScoreFirstStar:
                first_star.setImageResource(R.drawable.bi_star_fill__1_);
                second_star.setImageResource(R.drawable.star_unfilled);
                third_star.setImageResource(R.drawable.star_unfilled);
                fourth_star.setImageResource(R.drawable.star_unfilled);
                fifth_star.setImageResource(R.drawable.star_unfilled);
                rating = 1;
                break;
            case R.id.ScoreSecondStar:
                first_star.setImageResource(R.drawable.bi_star_fill__1_);
                second_star.setImageResource(R.drawable.bi_star_fill__1_);
                third_star.setImageResource(R.drawable.star_unfilled);
                fourth_star.setImageResource(R.drawable.star_unfilled);
                fifth_star.setImageResource(R.drawable.star_unfilled);
                rating = 2;
                break;
            case R.id.ScoreThirdStar:
                first_star.setImageResource(R.drawable.bi_star_fill__1_);
                second_star.setImageResource(R.drawable.bi_star_fill__1_);
                third_star.setImageResource(R.drawable.bi_star_fill__1_);
                fourth_star.setImageResource(R.drawable.star_unfilled);
                fifth_star.setImageResource(R.drawable.star_unfilled);
                rating = 3;
                break;
            case R.id.ScoreFourthStar:
                first_star.setImageResource(R.drawable.bi_star_fill__1_);
                second_star.setImageResource(R.drawable.bi_star_fill__1_);
                third_star.setImageResource(R.drawable.bi_star_fill__1_);
                fourth_star.setImageResource(R.drawable.bi_star_fill__1_);
                fifth_star.setImageResource(R.drawable.star_unfilled);
                rating = 4;
                break;
            case R.id.ScoreFifthStar:
                first_star.setImageResource(R.drawable.bi_star_fill__1_);
                second_star.setImageResource(R.drawable.bi_star_fill__1_);
                third_star.setImageResource(R.drawable.bi_star_fill__1_);
                fourth_star.setImageResource(R.drawable.bi_star_fill__1_);
                fifth_star.setImageResource(R.drawable.bi_star_fill__1_);
                rating = 5;
                break;
        }
    }

    public void SendScore() throws JSONException {
        JSONObject data = new JSONObject();
        data.put("activityId",getevent.getEvent_id());
        data.put("userId",Global.studentid);
        data.put("rating",rating);
        data.put("comment",score_content.getText());
        Log.d("F4121 JSON","DATA: "+data.toString());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),data.toString());
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .RateActivity(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("F4121","onResponse ok");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.d("F4121","onFailure ");
            }
        });
    }
}

