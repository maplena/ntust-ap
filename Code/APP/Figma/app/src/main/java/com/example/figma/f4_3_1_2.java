package com.example.figma;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.Eventtest.RatingItem;
import com.example.figma.api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f4_3_1_2 extends Fragment {
    private static Gson gson;
    TextView end_post_score_name_view;
    TextView end_post_score_host_view;
    TextView end_post_score_tag_view;
    TextView end_post_score_sex_view;
    TextView end_post_score_time_view;
    TextView end_post_score_people_view;

    Button end_post_score_close;

    RecyclerView end_post_score;

    EndPostScoreAdapter end_post_score_adapter;
    List<RatingItem> end_post_score_list;

    ImageView rate_first_star;
    ImageView rate_second_star;
    ImageView rate_third_star;
    ImageView rate_fourth_star;
    ImageView rate_fifth_star;


    Event getevent;

    Double score;
    Integer page = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_3_1_2() {
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
    public static f4_3_1_2 newInstance(String param1, String param2) {
        f4_3_1_2 fragment = new f4_3_1_2();
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
        end_post_score_adapter = new EndPostScoreAdapter(getActivity());
        end_post_score_list = new ArrayList<>();
        Bundle get = getArguments();
        getevent = getGsonParser().fromJson(get.getString("event"),Event.class);
        getActivityRate();
        getActivityRateList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f4_3_1_2, container, false);
        // Inflate the layout for this fragment

        end_post_score_name_view = view.findViewById(R.id.EndPostScoreName);
        end_post_score_tag_view = view.findViewById(R.id.EndPostScoreTag);
        end_post_score_sex_view = view.findViewById(R.id.EndPostScoreSex);
        end_post_score_time_view = view.findViewById(R.id.EndPostScoreTime);
        end_post_score_people_view = view.findViewById(R.id.EndPostScorePeople);
        end_post_score_host_view = view.findViewById(R.id.EndPostScoreHost);
        end_post_score_close = view.findViewById(R.id.EndPostScoreCloseButton);
        end_post_score_close.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_3_1_2_to_f4_2_1));

        end_post_score = view.findViewById(R.id.EndPostScoreRecycleView);
        end_post_score.setLayoutManager(new LinearLayoutManager(getActivity()));
        end_post_score.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1;
                    getActivityRateList();
                    recyclerView.post(() -> end_post_score_adapter.notifyDataSetChanged());
                }
            }
        });

        end_post_score.setAdapter(end_post_score_adapter);
        end_post_score_adapter.setTasks(end_post_score_list);
        rate_first_star = view.findViewById(R.id.RateFirstStar);
        rate_second_star = view.findViewById(R.id.RateSecondStar);
        rate_third_star = view.findViewById(R.id.RateThirdStar);
        rate_fourth_star = view.findViewById(R.id.RateFourthStar);
        rate_fifth_star = view.findViewById(R.id.RateFifthStar);
        end_post_score_name_view.setText(getevent.getEvent_name());

        
        return view;
    }
    public Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }

    public void getActivityRateList(){
        Call<List<RatingItem>> call_get_user_public = RetrofitClient
                .getInstance()
                .getAPI()
                .getActivityRateList(getevent.getEvent_id().toString(),page);

        call_get_user_public.enqueue(new Callback<List<RatingItem>>() {
            @Override
            public void onResponse(Call<List<RatingItem>> call, Response<List<RatingItem>> response) {

                Log.d("f4_2_1","getPostData : receive");
                List<RatingItem> ratingItemList = new LinkedList<>();
                List<RatingItem> Temp_list = new ArrayList<>();
                try {
                    if (response.body()!=null) {
                        ratingItemList = response.body();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Temp_list.addAll(ratingItemList);
                } catch (Exception  e) {
                    e.printStackTrace();
                }
                end_post_score_list.addAll(Temp_list);
                end_post_score_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RatingItem>> call, Throwable throwable) {

            }
        });
    }


    public void getActivityRate(){
        Call<ResponseBody> call_get_user_public = RetrofitClient
                .getInstance()
                .getAPI()
                .getActivityRate(getevent.getEvent_id().toString());

        call_get_user_public.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = "0";
                try {
                    if (response.body()!=null)s = response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Get response.body Fail");
                }
                System.out.println(s);
                score = Double.parseDouble(s);
                JudgeRate();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    public void JudgeRate(){
        if((score * 10) % 10 >= 4){
            switch (score.intValue()){
                case 1:
                    rate_first_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_second_star.setForeground(getActivity().getDrawable(R.drawable.new_star_half));
                    rate_third_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    rate_fourth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    rate_fifth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    break;
                case 2:
                    rate_first_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_second_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_third_star.setForeground(getActivity().getDrawable(R.drawable.new_star_half));
                    rate_fourth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    rate_fifth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    break;
                case 3:
                    rate_first_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_second_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_third_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_fourth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_half));
                    rate_fifth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    break;
                case 4:
                    rate_first_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_second_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_third_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_fourth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_fifth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_half));
                    break;
            }
        }
        else {
            switch (score.intValue()){
                case 1:
                    rate_first_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_second_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    rate_third_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    rate_fourth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    rate_fifth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    break;
                case 2:
                    rate_first_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_second_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_third_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    rate_fourth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    rate_fifth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    break;
                case 3:
                    rate_first_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_second_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_third_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_fourth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    rate_fifth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    break;
                case 4:
                    rate_first_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_second_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_third_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_fourth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_fifth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_empty));
                    break;
                case 5:
                    rate_first_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_second_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_third_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_fourth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    rate_fifth_star.setForeground(getActivity().getDrawable(R.drawable.new_star_fill));
                    break;
            }
        }
    }
}


