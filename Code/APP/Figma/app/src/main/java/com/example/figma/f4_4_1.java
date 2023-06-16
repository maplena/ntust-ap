package com.example.figma;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

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
public class f4_4_1 extends Fragment {
    private static Gson gson;
    ImageButton sign_up_back;
    Button want_enter_event_name;

    Button confirm_join;
    Button input_lock;
    Button input_unlock;

    Event getevent;

    EditText join_name;
    EditText join_department;
    EditText join_grade;
    EditText join_phone;

    ToggleButton join_ntu;
    ToggleButton join_ntnu;
    ToggleButton join_ntust;

    ToggleButton join_boy;
    ToggleButton join_girl;

    ToggleButton join_meat;
    ToggleButton join_veg;

    Boolean meat = true,veg = false;

    Integer grade;
    Integer school = 3;
    Integer gender = 1;
    Integer meal = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_4_1() {
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
    public static f4_4_1 newInstance(String param1, String param2) {
        f4_4_1 fragment = new f4_4_1();
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
        View view = inflater.inflate(R.layout.f4_4_1, container, false);
        // Inflate the layout for this fragment
        Bundle get = getArguments();
        getevent = getGsonParser().fromJson(get.getString("event"),Event.class);
        want_enter_event_name = view.findViewById(R.id.WantEnterEventName);
        want_enter_event_name.setText(getevent.getEvent_name());
        sign_up_back = view.findViewById(R.id.SignUpBackButton);
        sign_up_back.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_4_1_to_f4_5));
        input_lock = view.findViewById(R.id.JoinLock);
        input_lock.setOnClickListener(view12 -> {
            lock();
            input_lock.setVisibility(View.INVISIBLE);
            input_unlock.setVisibility(View.VISIBLE);
            confirm_join.setVisibility(View.VISIBLE);
        });
        input_unlock = view.findViewById(R.id.JoinUnLock);
        input_unlock.setOnClickListener(view1 -> {
            unLock();
            input_lock.setVisibility(View.VISIBLE);
            input_unlock.setVisibility(View.INVISIBLE);
            confirm_join.setVisibility(View.INVISIBLE);
        });

        confirm_join = view.findViewById(R.id.ConfirmJoin);
        confirm_join.setOnClickListener(v -> {
            //TODO:加入
            GradeJudge();
            try {
                JoinActivity();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Navigation.findNavController(v).navigate(R.id.action_f4_4_1_to_f4_1_1);
        });
        input_unlock.setVisibility(View.INVISIBLE);
        confirm_join.setVisibility(View.INVISIBLE);

        join_name = view.findViewById(R.id.JoinName);
        join_grade = view.findViewById(R.id.JoinGrade);
        join_phone = view.findViewById(R.id.JoinPhone);
        join_department = view.findViewById(R.id.JoinDepartment);

        join_ntu = view.findViewById(R.id.JoinNTU);
        join_ntnu = view.findViewById(R.id.JoinNTNU);
        join_ntust = view.findViewById(R.id.JoinNTUST);
        join_ntu.setOnClickListener(this::SchoolClick);
        join_ntnu.setOnClickListener(this::SchoolClick);
        join_ntust.setOnClickListener(this::SchoolClick);

        join_boy = view.findViewById(R.id.JoinBoy);
        join_girl = view.findViewById(R.id.JoinGirl);
        join_boy.setOnClickListener(this::GenderClick);
        join_girl.setOnClickListener(this::GenderClick);

        join_meat = view.findViewById(R.id.JoinMeat);
        join_veg = view.findViewById(R.id.JoinVeg);
        join_meat.setOnClickListener(this::MealClick);
        join_veg.setOnClickListener(this::MealClick);
        return view;
    }



    public static Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }

    /*
    private Long activityId;

    private String userId;

    private String requestUserId;//僅kick時需要用到
    private String userName;
    private int school;// 學校 1=NTU 2=NTNU 3=NTUST
    private String department;
    private int grade;// 年級 1=大一 3=大三 5=碩士 6=博士
    private String phone;
    private int meal;//葷=1, 素=2, 都有供應=3
    private int gender;//男=1, 女=2
     */
    public void JoinActivity() throws JSONException {
        JSONObject data = new JSONObject();
        data.put("activityId",getevent.getEvent_id());
        data.put("userId",Global.studentid);
        data.put("requestUserId",Global.studentid);
        data.put("userName",Global.name);
        data.put("school",Global.school_num+1);//client的台大是0 server是1
        data.put("grade",Global.userGrade);
        data.put("phone",join_phone.getText().toString());
        data.put("meal",meal);
        if (!Global.gender)data.put("gender",1);//當gender是0時表男性 對應到server的1
        else data.put("gender",2);//當gender是1時表女性 對應到server的2


        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),data.toString());

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .JoinActivity(body);
        Log.d("F441","onResponse : send"+data.toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("F441","onResponse : receive"+response.toString());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void GenderClick(View v){
        switch (v.getId()){
            case R.id.JoinBoy:
                join_boy.setTextColor(getActivity().getColor(R.color.black));
                join_girl.setTextColor(0xFF808080);
                gender = 1;
                break;
            case R.id.JoinGirl:
                join_boy.setTextColor(0xFF808080);
                join_girl.setTextColor(getActivity().getColor(R.color.black));
                gender = 2;
                break;
        }
    }

    public void SchoolClick(View v){
        switch (v.getId()){
            case R.id.JoinNTU:
                join_ntu.setTextColor(getActivity().getColor(R.color.black));
                join_ntnu.setTextColor(0xFF808080);
                join_ntust.setTextColor(0xFF808080);
                school = 1;
                break;
            case R.id.JoinNTNU:
                join_ntu.setTextColor(0xFF808080);
                join_ntnu.setTextColor(getActivity().getColor(R.color.black));
                join_ntust.setTextColor(0xFF808080);
                school = 2;
                break;
            case R.id.JoinNTUST:
                join_ntu.setTextColor(0xFF808080);
                join_ntnu.setTextColor(0xFF808080);
                join_ntust.setTextColor(getActivity().getColor(R.color.black));
                school = 3;
                break;
        }
    }

    public void MealClick(View v){
        switch (v.getId()){
            case R.id.JoinMeat:
                if(meat){
                    join_meat.setTextColor(0xFF808080);
                    meat = false;
                }
                else{
                    join_meat.setTextColor(getActivity().getColor(R.color.black));
                    meat = true;
                }
                break;
            case R.id.JoinVeg:
                if(veg){
                    join_veg.setTextColor(0xFF808080);
                    veg = false;
                }
                else{
                    join_veg.setTextColor(getActivity().getColor(R.color.black));
                    veg = true;
                }
                break;
        }
        if(meat && veg)meal = 3;
        if(meat && !veg)meal = 1;
        if(!meat && veg)meal = 2;
        if(!meat && !veg)meal = 0;//TODO:處理都不選
    }

    public void GradeJudge(){
        String[] grade_input = join_grade.getText().toString().split(" ");
        Integer need = 0;
        for(String data : grade_input){
            switch (data){
                case "大一":
                    need = 1;
                    break;
                case "大二":
                    need = 2;
                    break;
                case "大三":
                    need = 3;
                    break;
                case "大四":
                    need = 4;
                    break;
                case "碩士":
                    need = 5;
                    break;
                case "博士":
                    need = 6;
                    break;
                case "":
                    need = 0;
                    break;
            }
        }
        grade = need;
    }

    public void lock(){
        join_name.setEnabled(false);
        join_grade.setEnabled(false);
        join_phone.setEnabled(false);
        join_department.setEnabled(false);
        try {

            switch (school){
                case 1:
                    join_ntu.setEnabled(false);
                    join_ntnu.setVisibility(View.INVISIBLE);
                    join_ntust.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    join_ntu.setVisibility(View.INVISIBLE);
                    join_ntnu.setEnabled(false);
                    join_ntust.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    join_ntu.setVisibility(View.INVISIBLE);
                    join_ntnu.setVisibility(View.INVISIBLE);
                    join_ntust.setEnabled(false);
                    break;

            }
            switch (gender){
                case 1:
                    join_boy.setEnabled(false);
                    join_girl.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    join_boy.setVisibility(View.INVISIBLE);
                    join_girl.setEnabled(false);
                    break;
            }
            switch (meal){
                case 1:
                    join_meat.setEnabled(false);
                    join_veg.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    join_meat.setVisibility(View.INVISIBLE);
                    join_veg.setEnabled(false);
                    break;
                case 3:
                    join_meat.setEnabled(false);
                    join_veg.setEnabled(false);
                    break;
            }
        }
        catch (Exception e){

        }
    }

    public void unLock(){
        join_name.setEnabled(true);
        join_grade.setEnabled(true);
        join_phone.setEnabled(true);
        join_department.setEnabled(true);
        try {

            switch (school){
                case 1:
                    join_ntu.setEnabled(true);
                    join_ntnu.setVisibility(View.VISIBLE);
                    join_ntust.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    join_ntu.setVisibility(View.VISIBLE);
                    join_ntnu.setEnabled(true);
                    join_ntust.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    join_ntu.setVisibility(View.VISIBLE);
                    join_ntnu.setVisibility(View.VISIBLE);
                    join_ntust.setEnabled(true);
                    break;

            }
            switch (gender){
                case 1:
                    join_boy.setEnabled(true);
                    join_girl.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    join_boy.setVisibility(View.VISIBLE);
                    join_girl.setEnabled(true);
                    break;
            }
            switch (meal){
                case 1:
                    join_meat.setEnabled(true);
                    join_veg.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    join_meat.setVisibility(View.VISIBLE);
                    join_veg.setEnabled(true);
                    break;
                case 3:
                    join_meat.setEnabled(true);
                    join_veg.setEnabled(true);
                    break;
            }
        }
        catch (Exception e){

        }

    }
}
