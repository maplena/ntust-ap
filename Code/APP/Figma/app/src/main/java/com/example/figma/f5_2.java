package com.example.figma;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.api.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f5_2 extends Fragment {
    FloatingActionButton question_manage_post;
    Button to_question_list;
    Button post_question_list;
    Button end_question_list;

    ImageView question_manage_to_lottery;
    ImageView question_manage_to_question;
    ImageView question_manage_to_main;
    ImageView question_manage_to_event;
    ImageView question_manage_to_personal;
    RecyclerView question_manage_list;
    PostQuestionListAdapter post_question_list_adapter;
    EndQuestionListAdapter end_question_list_adapter;

    List<Question> post_survey_list;
    List<Question> post_end_survey_list;

    Integer page=0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f5_2() {
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
    public static f5_2 newInstance(String param1, String param2) {
        f5_2 fragment = new f5_2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("F52","onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        post_question_list_adapter = new PostQuestionListAdapter(getActivity());
        end_question_list_adapter = new EndQuestionListAdapter(getActivity());
        post_survey_list = new ArrayList<>();
        post_end_survey_list = new ArrayList<>();
        getPostData();
        getEndPostData();
//        post_question_list_adapter.setTasks(post_survey_list);
//        end_question_list_adapter.setTasks(post_end_survey_list);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f5_2, container, false);
        Log.d("F52","onCreateView");
        // Inflate the layout for this fragment
        question_manage_list = view.findViewById(R.id.QuestionManageRecyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(question_manage_list.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getDrawable(R.drawable.split_line2));
        question_manage_list.addItemDecoration(dividerItemDecoration);
        //幫recyclerview增加layout是必要的,不然顯示不出來
        question_manage_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        question_manage_list.setAdapter(post_question_list_adapter);
        post_question_list_adapter.setTasks(post_survey_list);
        end_question_list_adapter.setTasks(post_end_survey_list);

        question_manage_post = view.findViewById(R.id.QuestionManagePostQuestionButton);
        question_manage_post.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_2_to_f5_2_1));

        to_question_list = view.findViewById(R.id.ToQuestionListButton);
        to_question_list.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_2_to_f5_1));

        post_question_list = view.findViewById(R.id.PostQuestionListButton);
        post_question_list.setOnClickListener(v -> {
            post_question_list.setTextColor(Color.rgb(3, 218, 197));
            end_question_list.setTextColor(Color.rgb(198, 198, 198));
            question_manage_list.setAdapter(post_question_list_adapter);
            post_question_list_adapter.setTasks(post_survey_list);
        });
        end_question_list = view.findViewById(R.id.EndQuestionListButton);
        end_question_list.setOnClickListener(v -> {
            end_question_list.setTextColor(Color.rgb(3, 218, 197));
            post_question_list.setTextColor(Color.rgb(198, 198, 198));
            question_manage_list.setAdapter(end_question_list_adapter);
        });




        question_manage_to_lottery = view.findViewById(R.id.QuestionManageToLottery);
        question_manage_to_lottery.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_f5_2_to_f6_1);
        });
        question_manage_to_question = view.findViewById(R.id.QuestionManageToQuestion);
        question_manage_to_question.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_2_to_f5_1));
        question_manage_to_main = view.findViewById(R.id.QuestionManageToMain);
        question_manage_to_main.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_2_to_f2_1));
        question_manage_to_event = view.findViewById(R.id.QuestionManageToEvent);
        question_manage_to_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_2_to_f4_1));
        question_manage_to_personal = view.findViewById(R.id.QuestionManageToPersonal);
        question_manage_to_personal.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_2_to_f3_1));


        return view;
    }

    public void getPostData(){
        Call<List<Question>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getSurveyByHost(Global.studentid,"running",page);
        Log.d("F52","getPostData : send");
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                Log.d("F52","getPostData : receive");
                List<Question> surveyList = new ArrayList<>();
                try {
                    if (response.body()!=null) {
                        surveyList= response.body();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                post_survey_list.addAll(surveyList);
                post_question_list_adapter.setTasks(post_survey_list);
                //post_question_list_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void getEndPostData(){
        Call<List<Question>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getSurveyByHost(Global.studentid,"end",page);
        Log.d("F52","getEndPostData : send");
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                Log.d("F52","getPostData : receive");
                List<Question> surveyList = new ArrayList<>();
                try {
                    if (response.body()!=null) {
                        surveyList= response.body();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                post_end_survey_list.addAll(surveyList);
                end_question_list_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }




}

