package com.example.figma;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.api.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f5_1 extends Fragment {
    FloatingActionButton question_list_post;
    Button to_question_manage;

    ImageButton question_to_notification;
    ImageButton question_to_chat;

    ImageView question_to_lottery;
    ImageView question;
    ImageView question_to_main;
    ImageView question_to_event;
    ImageView question_to_personal;

    List<Question> survey_list;
    RecyclerView question_list;
    QuestionListAdapter question_list_adapter;

    Integer page;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f5_1() {
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
    public static f5_1 newInstance(String param1, String param2) {
        f5_1 fragment = new f5_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("F51","OnCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        question_list_adapter = new QuestionListAdapter(getActivity());
        survey_list = new ArrayList<>();
        page = 0;
        try {
            getSurveyListByLimit();
            question_list_adapter.setTasks(survey_list);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f5_1, container, false);
        Log.d("F51","onCreateView");
        // Inflate the layout for this fragment
        question_list = view.findViewById(R.id.QuestionListRecyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(question_list.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getDrawable(R.drawable.split_line2));
        question_list.addItemDecoration(dividerItemDecoration);
        //幫recyclerview增加layout是必要的,不然顯示不出來
        question_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        question_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1;
                    try {
                        getSurveyListByLimit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.post(() -> question_list_adapter.notifyDataSetChanged());
                }
            }
        });
        question_list.setAdapter(question_list_adapter);
        question_list_adapter.setTasks(survey_list);
        question_to_notification = view.findViewById(R.id.QuestionToNotification);
        question_to_notification.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_1_to_f9));
        question_to_chat = view.findViewById(R.id.QuestionToChat);
        question_to_chat.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_1_to_f8));

        question_list_post = view.findViewById(R.id.QuestionListPostButton);
        question_list_post.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_1_to_f5_2_1));

        to_question_manage = view.findViewById(R.id.ToQuestionManageButton);
        to_question_manage.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_1_to_f5_2));
        question_to_lottery = view.findViewById(R.id.QuestionToLottery);
        question_to_lottery.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_1_to_f6_1));
        question = view.findViewById(R.id.Question);
        question_to_main = view.findViewById(R.id.QuestionToMain);
        question_to_main.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_1_to_f2_1));
        question_to_event = view.findViewById(R.id.QuestionToEvent);
        question_to_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_1_to_f4_1));
        question_to_personal = view.findViewById(R.id.QuestionToPersonal);
        question_to_personal.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_1_to_f3_1));
        return view;
    }

    public void getSurveyListByLimit() throws JSONException {
        JSONObject data = new JSONObject();
        data.put("school_limit",Global.school_num+1);
        //android的school num    0是ntu 1是ntnu 2是ntust
        // server的school limit  0是不限 1是ntu 2是ntnu 3是ntust
        // 與server的school limit 有出入
        if(Global.gender)data.put("gender",1);
        else data.put("gender",2);
        data.put("grade",Global.userGrade);
        data.put("page",page);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),data.toString());

        Call<List<Question>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getSurveyByLimit(body);
        //2022 0214 CLH 此處response物件直接改為List of EventItem (新物件，不影響Event.java) 來避免使用JSON輪詢
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                Log.d("f5_1 OnResponse","ok");
                List<Question> surveyItemList = new ArrayList<>();
                //List<Question> Temp_list = new ArrayList<>();
                try {
                    if (response.body() != null) {
                        //s = response.body().string();
                        surveyItemList = response.body();
                        Log.d("f5_1 OnResponse","yes");

                    }
                } catch (Exception e) {
//                            e.printStackTrace();
                }
                survey_list.addAll(surveyItemList);
                question_list_adapter.setTasks(survey_list);

            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                t.printStackTrace();
            }


        });
    }

}
