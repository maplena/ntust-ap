package com.example.figma;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.figma.api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
public class f4_2_3 extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static Gson gson;
    ImageButton back_to_manage;
    Button accept_post;
    Button modify_data;
    Button accept_modify_data;
    Button activity_upload_picture;
    EditText activity_name;
    EditText activity_creator;
    EditText activity_grade;
    EditText activity_phone;
    EditText activity_limit;
    EditText activity_registration_deadline;
    EditText activity_position;
    EditText activity_start_date;
    EditText activity_end_date;
    EditText activity_tag;
    EditText activity_price;
    EditText activity_detail;
    View view;
    Boolean accept = false;
    Integer gender;
    Integer meal;
    Integer school_limit;
    Integer grade_limit;
    String type;
    Boolean ntust = true, ntnu = false, ntu = false;
    Boolean boy = true, girl = false;
    Boolean meat = true, veg = false;
    Boolean revise = false;

    ToggleButton activity_personal;
    ToggleButton activity_club;
    ToggleButton activity_ntust;
    ToggleButton activity_ntnu;
    ToggleButton activity_ntu;
    ToggleButton activity_boy;
    ToggleButton activity_girl;
    ToggleButton activity_meat;
    ToggleButton activity_vegetarian;

    ImageView preview;

    Event getevent;
    final Calendar myCalendar = Calendar.getInstance();//use in date picker
    int who_invoke_timepicker;
    String chosen_image_path;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_2_3() {
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
    public static f4_2_3 newInstance(String param1, String param2) {
        f4_2_3 fragment = new f4_2_3();
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
        view = inflater.inflate(R.layout.f4_2_3, container, false);
        // Inflate the layout for this fragment
        initialization(view);//指派各個物件
        Bundle get = getArguments();

        if (get != null && get.containsKey("event")) {
            getevent = getGsonParser().fromJson(get.getString("event"), Event.class);
            revise = true;
            ReviseData();
        }

        back_to_manage.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_2_3_to_f4_2_1));
        modify_data.setOnClickListener(v -> {
            accept = false;
            modify_data.setVisibility(View.INVISIBLE);
            accept_post.setVisibility(View.INVISIBLE);
            accept_modify_data.setVisibility(View.VISIBLE);
            InputUnLocK();
        });

        accept_modify_data.setOnClickListener(v -> {
            accept = true;
            modify_data.setVisibility(View.VISIBLE);
            accept_post.setVisibility(View.VISIBLE);
            accept_modify_data.setVisibility(View.INVISIBLE);
            InputLock();
        });

        accept_post.setOnClickListener(v -> {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.TAIWAN);
            if (accept) {
                //String[] school = activity_position.getText().toString().split(" ");



                Date open_date = new Date();
                //修改活動
                if (revise) {
                    try {


                        File f = new File(getContext().getCacheDir(), "temp.jpg");
                        f.createNewFile();
                        //Convert bitmap to byte array
                        Bitmap bitmap = Global.bitmap;

                        if(bitmap!=null){
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                            byte[] bitmapdata = bos.toByteArray();
                            //write the bytes in file
                            FileOutputStream fos = new FileOutputStream(f);
                            fos.write(bitmapdata);
                            fos.flush();
                            fos.close();
                        }
                        else f=null;


                        GradeJudge();
                        JSONObject data = new JSONObject();
                        data.put("title", activity_name.getText().toString());
                        data.put("description", activity_detail.getText().toString());
                        data.put("image_path", "image_path");
                        data.put("host", Global.name);
                        data.put("hostID", Global.studentid);
                        data.put("host_email", Global.email);
                        data.put("host_phone_number", activity_phone.getText().toString());
                        data.put("school", "無");
                        data.put("place", activity_position.getText().toString());
                        //data.put("school", school[0]);
//                            if (school.length==1)data.put("place", "");
//                            else data.put("place", school[1]);
                        data.put("tag", activity_tag.getText().toString());
                        data.put("type", type);
                        data.put("school_limit", school_limit);
                        data.put("gender", gender);
                        data.put("people_num_limit", Integer.parseInt(activity_limit.getText().toString()));
                        data.put("price", Integer.parseInt(activity_price.getText().toString()));
                        data.put("meal", meal);
                        data.put("grade", grade_limit);
                        data.put("open_date", format.format(open_date));
                        data.put("deadline_date", activity_registration_deadline.getText().toString());
                        data.put("start_date", activity_start_date.getText().toString());
                        data.put("end_date", activity_end_date.getText().toString());
                        data.put("status", "running");
                        //以下為增加及更新活動所需參數
                        switch (Global.school_num) {
                            case 0:
                                data.put("userSchool", 1);
                                break;
                            case 1:
                                data.put("userSchool", 2);
                                break;
                            case 2:
                                data.put("userSchool", 3);
                                break;
                        }
                        data.put("userGrade", Global.userGrade);
                        if (Global.gender) data.put("userGender", 1);
                        else data.put("userGender", 2);
                        data.put("userMeal", 0);
                        //以下為更新活動所需參數
                        data.put("id", getevent.getEvent_id());
                        data.put("requestUserId", Global.studentid);

                        //RequestBody body = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data;boundary=, charset=utf-8"), data.toString());
                        RequestBody request =
                                RequestBody.create(MediaType.parse("application/json"), data.toString());

                        MultipartBody.Part body;
                        if(f!=null){
                            Log.d("F423","含圖片");
                            RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"), f);
                            body = MultipartBody.Part.createFormData("img", f.getName(), fbody);
                            UpdateActivity(request, body);
                        }else {
                            Log.d("F423","不含圖片");
                            UpdateActivity(request, null);
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {

                        File f = new File(getContext().getCacheDir(), "temp.jpg");
                        f.createNewFile();

                        //Convert bitmap to byte array
                        Bitmap bitmap = Global.bitmap;
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                        byte[] bitmapdata = bos.toByteArray();

                        //write the bytes in file
                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();

                        GradeJudge();
                        JSONObject data = new JSONObject();
                        data.put("title", activity_name.getText().toString());
                        data.put("description", activity_detail.getText().toString());
                        data.put("image_path", "image_path");
                        data.put("host", Global.name);
                        data.put("hostID", Global.studentid);
                        data.put("host_email", Global.email);
                        data.put("host_phone_number", activity_phone.getText().toString());
                        data.put("school", "無");
                        data.put("place", activity_position.getText().toString());
                        data.put("tag", activity_tag.getText().toString());
                        data.put("type", type);
                        data.put("school_limit", school_limit);
                        data.put("gender", gender);
                        data.put("people_num_limit", Integer.parseInt(activity_limit.getText().toString()));
                        data.put("price", Integer.parseInt(activity_price.getText().toString()));
                        data.put("meal", meal);
                        data.put("grade", grade_limit);
                        data.put("open_date", format.format(open_date));
                        data.put("deadline_date", activity_registration_deadline.getText().toString());
                        data.put("start_date", activity_start_date.getText().toString());
                        data.put("end_date", activity_end_date.getText().toString());
                        data.put("status", "running");
                        switch (Global.school_num) {
                            case 0:
                                data.put("userSchool", 1);
                                break;
                            case 1:
                                data.put("userSchool", 2);
                                break;
                            case 2:
                                data.put("userSchool", 3);
                                break;
                        }
                        data.put("userGrade", Global.userGrade);
                        if (Global.gender) data.put("userGender", 1);
                        else data.put("userGender", 2);
                        data.put("userMeal", 0);

                        //RequestBody body = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data;boundary=, charset=utf-8"), data.toString());
                        RequestBody request =
                                RequestBody.create(MediaType.parse("application/json"), data.toString());
//                            MultipartBody.Part body =
//                                    MultipartBody.Part.createFormData("files", f.getName(), requestFile);
                        RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"), f);
                        MultipartBody.Part body =
                                MultipartBody.Part.createFormData("img", f.getName(), fbody);
                        AddActivity(request, body);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Navigation.findNavController(v).navigate(R.id.action_f4_2_3_to_f4_2_1);
            }

        });
        return view;
    }

    public static Gson getGsonParser() {
        if (null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }

    public void initialization(View view) {
        activity_name = view.findViewById(R.id.ActivityName);
        activity_creator = view.findViewById(R.id.ActivityCreator);
        activity_grade = view.findViewById(R.id.ActivityGrade);
        activity_phone = view.findViewById(R.id.ActivityPhone);
        activity_limit = view.findViewById(R.id.ActivityLimit);
        activity_registration_deadline = view.findViewById(R.id.ActivityRegistrationDeadline);
        activity_position = view.findViewById(R.id.ActivityPosition);
        activity_start_date = view.findViewById(R.id.ActivityStartDate);
        activity_end_date = view.findViewById(R.id.ActivityEndDate);
        activity_tag = view.findViewById(R.id.ActivityTag);
        activity_price = view.findViewById(R.id.ActivityPrice);
        activity_detail = view.findViewById(R.id.ActivityDetail);
        back_to_manage = view.findViewById(R.id.PostBackButton);
        accept_post = view.findViewById(R.id.AcceptPostButton);
        modify_data = view.findViewById(R.id.ModifyActivityDataButton);
        activity_upload_picture = view.findViewById(R.id.ActivityPictureUploadButton);
        accept_modify_data = view.findViewById(R.id.AcceptModifyActivityDataButton);
        activity_personal = view.findViewById(R.id.ActivityPersonalButton);
        activity_club = view.findViewById(R.id.ActivityClubButton);
        activity_ntust = view.findViewById(R.id.ActivityNTUSTButton);
        activity_ntnu = view.findViewById(R.id.ActivityNTNUButton);
        activity_ntu = view.findViewById(R.id.ActivityNTUButton);
        activity_boy = view.findViewById(R.id.ActivityBoyButton);
        activity_girl = view.findViewById(R.id.ActivityGirlButton);
        activity_meat = view.findViewById(R.id.ActivityMeatButton);
        activity_vegetarian = view.findViewById(R.id.ActivityVegetarianButton);
        activity_personal.setOnClickListener(this::ClubClick);
        activity_club.setOnClickListener(this::ClubClick);
        activity_ntust.setOnClickListener(this::SchoolClick);
        activity_ntnu.setOnClickListener(this::SchoolClick);
        activity_ntu.setOnClickListener(this::SchoolClick);
        activity_boy.setOnClickListener(this::GenderClick);
        activity_girl.setOnClickListener(this::GenderClick);
        activity_meat.setOnClickListener(this::MealClick);
        activity_vegetarian.setOnClickListener(this::MealClick);
        modify_data.setVisibility(View.INVISIBLE);
        accept_post.setVisibility(View.INVISIBLE);
        preview = view.findViewById(R.id.UploadImagePreview);
        activity_limit.setText("10");
        activity_price.setText("0");
        type = "自辦";
        school_limit = 3;
        gender = 1;
        meal = 1;

        activity_start_date.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), f4_2_3.this, year, month, day);
            datePickerDialog.show();
            who_invoke_timepicker = 0;
        });
        activity_end_date.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), f4_2_3.this, year, month, day);
            datePickerDialog.show();
            who_invoke_timepicker = 1;
        });
        activity_registration_deadline.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), f4_2_3.this, year, month, day);
            datePickerDialog.show();
            who_invoke_timepicker = 2;
        });

        activity_upload_picture.setOnClickListener(v -> {
            Log.d("F423", "上傳圖片");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 21);
            preview.setImageBitmap(Global.bitmap);

        });
    }

    public void ClubClick(View v) {
        switch (v.getId()) {
            case R.id.ActivityPersonalButton:
                activity_personal.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_personal.setTextColor(getActivity().getColor(R.color.black));
                activity_club.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_club.setTextColor(0xFF808080);
                type = "自辦";
                break;
            case R.id.ActivityClubButton:
                activity_club.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_club.setTextColor(getActivity().getColor(R.color.black));
                activity_personal.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_personal.setTextColor(0xFF808080);
                type = "社辦";
                break;
        }
    }

    public void SchoolClick(View v) {
        switch (v.getId()) {
            case R.id.ActivityNTUSTButton:
                if (ntust) {
                    activity_ntust.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                    activity_ntust.setTextColor(0xFF808080);
                    ntust = false;
                } else {
                    activity_ntust.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                    activity_ntust.setTextColor(getActivity().getColor(R.color.black));
                    ntust = true;
                }
                break;
            case R.id.ActivityNTNUButton:
                if (ntnu) {
                    activity_ntnu.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                    activity_ntnu.setTextColor(0xFF808080);
                    ntnu = false;
                } else {
                    activity_ntnu.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                    activity_ntnu.setTextColor(getActivity().getColor(R.color.black));
                    ntnu = true;
                }
                break;
            case R.id.ActivityNTUButton:
                if (ntu) {
                    activity_ntu.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                    activity_ntu.setTextColor(0xFF808080);
                    ntu = false;
                } else {
                    activity_ntu.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                    activity_ntu.setTextColor(getActivity().getColor(R.color.black));
                    ntu = true;
                }
                break;
        }
        if (ntust && ntnu && ntu) school_limit = 0;
        if (!ntust && ntnu && ntu) school_limit = 12;
        if (ntust && !ntnu && ntu) school_limit = 13;
        if (ntust && ntnu && !ntu) school_limit = 23;
        if (ntust && !ntnu && !ntu) school_limit = 3;
        if (!ntust && ntnu && !ntu) school_limit = 2;
        if (!ntust && !ntnu && ntu) school_limit = 1;
        if (!ntust && !ntnu && !ntu) school_limit = 4;//處理都不選
    }

    public void GenderClick(View v) {
        switch (v.getId()) {
            case R.id.ActivityBoyButton:
                if (boy) {
                    activity_boy.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                    activity_boy.setTextColor(0xFF808080);
                    boy = false;
                } else {
                    activity_boy.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                    activity_boy.setTextColor(getActivity().getColor(R.color.black));
                    boy = true;
                }

                break;
            case R.id.ActivityGirlButton:
                if (girl) {
                    activity_girl.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                    activity_girl.setTextColor(0xFF808080);
                    girl = false;
                } else {
                    activity_girl.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                    activity_girl.setTextColor(getActivity().getColor(R.color.black));
                    girl = true;
                }
                break;
        }
        if (boy && girl) {
            gender = 0;
        }
        if (!boy && girl) {
            gender = 2;
        }
        if (boy && !girl) {
            gender = 1;
        }
        if (!boy && !girl) {
            gender = 3;//TODO:處理未選擇性別
        }

    }

    public void MealClick(View v) {
        switch (v.getId()) {
            case R.id.ActivityMeatButton:
                if (meat) {
                    activity_meat.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                    activity_meat.setTextColor(0xFF808080);
                    meat = false;
                } else {
                    activity_meat.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                    activity_meat.setTextColor(getActivity().getColor(R.color.black));
                    meat = true;
                }
                break;
            case R.id.ActivityVegetarianButton:
                if (veg) {
                    activity_vegetarian.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                    activity_vegetarian.setTextColor(0xFF808080);
                    veg = false;
                } else {
                    activity_vegetarian.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                    activity_vegetarian.setTextColor(getActivity().getColor(R.color.black));
                    veg = true;
                }
                break;
        }
        if (meat && veg) meal = 3;
        if (meat && !veg) meal = 1;
        if (!meat && veg) meal = 2;
        if (!meat && !veg) meal = 0;//TODO:處理都不選
    }

    public void GradeJudge() {
        String[] grade = activity_grade.getText().toString().split(" ");
        String need = "";
        for (String data : grade) {
            switch (data) {
                case "大一":
                    need += "1";
                    break;
                case "大二":
                    need += "2";
                    break;
                case "大三":
                    need += "3";
                    break;
                case "大四":
                    need += "4";
                    break;
                case "碩士":
                    need += "5";
                    break;
                case "博士":
                    need += "6";
                    break;
                case "":
                    need = "0";
                    break;
            }
        }
        grade_limit = Integer.parseInt(need);
    }

    public void AddActivity(RequestBody request, MultipartBody.Part img) {
        Log.d("F423 AddActivity", "send");
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .addActivity(request, img);
        Log.d("F423 AddActivity", "had send");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("F423 onResponse", "receive");
                String s = "";
                try {
                    if (response.isSuccessful()) {
                        s = response.body().string();
                        Log.d("F423 onResponse status", "receive" + s);

                    } else Log.d("F423 onResponse status", "error");
                    //f (response.body() != null) s = response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        Global.bitmap = null;
    }

    public void UpdateActivity(RequestBody request, MultipartBody.Part img) {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .UpdateActivity(request, img);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = "";
                try {
                    if (response.body() != null) {
                        s = response.body().string();
                        System.out.println(s);
                    }

                } catch (IOException e) {
                            e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
            }
        });
        Global.bitmap = null;
    }

    public void ReviseData() {
        activity_name.setText(getevent.getEvent_name());
        activity_creator.setText(getevent.getEvent_host());
        activity_phone.setText(getevent.getEvent_host_phone());
        activity_limit.setText(getevent.getMax_people().toString());
        activity_registration_deadline.setText(getevent.DateToString(getevent.getEvent_deadline_date()));
        activity_position.setText(getevent.getEvent_place());
        activity_start_date.setText(getevent.DateToString(getevent.getEvent_start_date()));
        activity_end_date.setText(getevent.DateToString(getevent.getEvent_end_date()));
        activity_tag.setText(getevent.getEvent_tag());
        activity_price.setText(getevent.getEvent_price().toString());
        activity_detail.setText(getevent.getEvent_description());

        gender = getevent.getEvent_gender();
        school_limit = getevent.getEvent_school_limit();
        meal = getevent.getEvent_meal();
        grade_limit = getevent.getEvent_grade();

        switch (gender) {
            case 0:
                boy = true;
                girl = true;
                activity_boy.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_boy.setTextColor(getActivity().getColor(R.color.black));
                activity_girl.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_girl.setTextColor(getActivity().getColor(R.color.black));
                break;
            case 1:
                boy = true;
                girl = false;
                activity_boy.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_boy.setTextColor(getActivity().getColor(R.color.black));
                activity_girl.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_girl.setTextColor(0xFF808080);
                break;
            case 2:
                boy = false;
                girl = true;
                activity_boy.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_boy.setTextColor(0xFF808080);
                activity_girl.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_girl.setTextColor(getActivity().getColor(R.color.black));
                break;
        }

        switch (school_limit) {
            case 0:
                ntu = true;
                ntnu = true;
                ntust = true;
                activity_ntu.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntu.setTextColor(getActivity().getColor(R.color.black));
                activity_ntnu.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntnu.setTextColor(getActivity().getColor(R.color.black));
                activity_ntust.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntust.setTextColor(getActivity().getColor(R.color.black));
                break;
            case 1:
                ntu = true;
                ntnu = false;
                ntust = false;
                activity_ntu.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntu.setTextColor(getActivity().getColor(R.color.black));
                activity_ntnu.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_ntnu.setTextColor(0xFF808080);
                activity_ntust.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_ntust.setTextColor(0xFF808080);
                break;
            case 2:
                ntu = false;
                ntnu = true;
                ntust = false;
                activity_ntu.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_ntu.setTextColor(0xFF808080);
                activity_ntnu.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntnu.setTextColor(getActivity().getColor(R.color.black));
                activity_ntust.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_ntust.setTextColor(0xFF808080);
                break;
            case 3:
                ntu = false;
                ntnu = false;
                ntust = true;
                activity_ntu.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_ntu.setTextColor(0xFF808080);
                activity_ntnu.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_ntnu.setTextColor(0xFF808080);
                activity_ntust.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntust.setTextColor(getActivity().getColor(R.color.black));
                break;
            case 12:
                ntu = true;
                ntnu = true;
                ntust = false;
                activity_ntu.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntu.setTextColor(getActivity().getColor(R.color.black));
                activity_ntnu.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntnu.setTextColor(getActivity().getColor(R.color.black));
                activity_ntust.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_ntust.setTextColor(0xFF808080);
                break;
            case 13:
                ntu = true;
                ntnu = false;
                ntust = true;
                activity_ntu.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntu.setTextColor(getActivity().getColor(R.color.black));
                activity_ntnu.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_ntnu.setTextColor(0xFF808080);
                activity_ntust.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntust.setTextColor(getActivity().getColor(R.color.black));
                break;
            case 23:
                ntu = false;
                ntnu = true;
                ntust = true;
                activity_ntu.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_ntu.setTextColor(0xFF808080);
                activity_ntnu.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntnu.setTextColor(getActivity().getColor(R.color.black));
                activity_ntust.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_ntust.setTextColor(getActivity().getColor(R.color.black));
                break;
        }

        switch (meal) {
            case 1:
                meat = true;
                veg = false;
                activity_meat.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_meat.setTextColor(getActivity().getColor(R.color.black));
                activity_vegetarian.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_vegetarian.setTextColor(0xFF808080);
                break;
            case 2:
                meat = false;
                veg = true;
                activity_meat.setBackground(getActivity().getDrawable(R.drawable.button_style9));
                activity_meat.setTextColor(0xFF808080);
                activity_vegetarian.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_vegetarian.setTextColor(getActivity().getColor(R.color.black));
                break;
            case 3:
                meat = true;
                veg = true;
                activity_meat.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_meat.setTextColor(getActivity().getColor(R.color.black));
                activity_vegetarian.setBackground(getActivity().getDrawable(R.drawable.button_style10));
                activity_vegetarian.setTextColor(getActivity().getColor(R.color.black));
                break;
        }

        String gradeText = "";
        if (grade_limit.equals(0)) {
            activity_grade.setText("");
        } else {
            while (grade_limit >= 1) {
                Integer a = grade_limit % 10;
                switch (a) {
                    case 1:
                        gradeText += "大一 ";
                        break;
                    case 2:
                        gradeText += "大二";
                        break;
                    case 3:
                        gradeText += "大三";
                        break;
                    case 4:
                        gradeText += "大四";
                        break;
                    case 5:
                        gradeText += "碩士";
                        break;
                    case 6:
                        gradeText += "博士";
                }

                grade_limit /= 10;
            }
        }
        activity_grade.setText(gradeText);

    }

    public void InputLock() {
        activity_name.setEnabled(false);
        activity_creator.setEnabled(false);
        try {
            switch (type) {
                case "自辦":
                    activity_personal.setEnabled(false);
                    activity_club.setVisibility(View.INVISIBLE);
                    break;
                case "社辦":
                    activity_club.setEnabled(false);
                    activity_personal.setVisibility(View.INVISIBLE);
                    break;
            }
            switch (school_limit) {
                case 0:
                    activity_ntu.setEnabled(false);
                    activity_ntnu.setEnabled(false);
                    activity_ntust.setEnabled(false);
                    break;
                case 1:
                    activity_ntu.setEnabled(false);
                    activity_ntnu.setVisibility(View.INVISIBLE);
                    activity_ntust.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    activity_ntu.setVisibility(View.INVISIBLE);
                    activity_ntnu.setEnabled(false);
                    activity_ntust.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    activity_ntu.setVisibility(View.INVISIBLE);
                    activity_ntnu.setVisibility(View.INVISIBLE);
                    activity_ntust.setEnabled(false);
                    break;
                case 12:
                    activity_ntu.setEnabled(false);
                    activity_ntnu.setEnabled(false);
                    activity_ntust.setVisibility(View.INVISIBLE);
                    break;
                case 13:
                    activity_ntu.setEnabled(false);
                    activity_ntnu.setVisibility(View.INVISIBLE);
                    activity_ntust.setEnabled(false);
                    break;
                case 23:
                    activity_ntu.setVisibility(View.INVISIBLE);
                    activity_ntnu.setEnabled(false);
                    activity_ntust.setEnabled(false);
                    break;
            }
            switch (gender) {
                case 0:
                    activity_boy.setEnabled(false);
                    activity_girl.setEnabled(false);
                    break;
                case 1:
                    activity_boy.setEnabled(false);
                    activity_girl.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    activity_boy.setVisibility(View.INVISIBLE);
                    activity_girl.setEnabled(false);
                    break;
            }
            switch (meal) {
                case 1:
                    activity_meat.setEnabled(false);
                    activity_vegetarian.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    activity_meat.setVisibility(View.INVISIBLE);
                    activity_vegetarian.setEnabled(false);
                    break;
                case 3:
                    activity_meat.setEnabled(false);
                    activity_vegetarian.setEnabled(false);
                    break;
            }
        } catch (Exception e) {

        }

        activity_grade.setEnabled(false);
        activity_phone.setEnabled(false);
        activity_limit.setEnabled(false);
        activity_registration_deadline.setEnabled(false);
        activity_position.setEnabled(false);
        activity_start_date.setEnabled(false);
        activity_end_date.setEnabled(false);
        activity_tag.setEnabled(false);
        activity_price.setEnabled(false);
    }

    public void InputUnLocK() {
        activity_name.setEnabled(true);
        activity_creator.setEnabled(true);
        try {
            switch (type) {
                case "自辦":
                    activity_personal.setEnabled(true);
                    activity_club.setVisibility(View.VISIBLE);
                    break;
                case "社辦":
                    activity_club.setEnabled(true);
                    activity_personal.setVisibility(View.VISIBLE);
                    break;
            }
            switch (school_limit) {
                case 0:
                    activity_ntu.setEnabled(true);
                    activity_ntnu.setEnabled(true);
                    activity_ntust.setEnabled(true);
                    break;
                case 1:
                    activity_ntu.setEnabled(true);
                    activity_ntnu.setVisibility(View.VISIBLE);
                    activity_ntust.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    activity_ntu.setVisibility(View.VISIBLE);
                    activity_ntnu.setEnabled(true);
                    activity_ntust.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    activity_ntu.setVisibility(View.VISIBLE);
                    activity_ntnu.setVisibility(View.VISIBLE);
                    activity_ntust.setEnabled(true);
                    break;
                case 12:
                    activity_ntu.setEnabled(true);
                    activity_ntnu.setEnabled(true);
                    activity_ntust.setVisibility(View.VISIBLE);
                    break;
                case 13:
                    activity_ntu.setEnabled(true);
                    activity_ntnu.setVisibility(View.VISIBLE);
                    activity_ntust.setEnabled(true);
                    break;
                case 23:
                    activity_ntu.setVisibility(View.VISIBLE);
                    activity_ntnu.setEnabled(true);
                    activity_ntust.setEnabled(true);
                    break;
            }
            switch (gender) {
                case 0:
                    activity_boy.setEnabled(true);
                    activity_girl.setEnabled(true);
                    break;
                case 1:
                    activity_boy.setEnabled(true);
                    activity_girl.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    activity_boy.setVisibility(View.VISIBLE);
                    activity_girl.setEnabled(true);
                    break;
            }
            switch (meal) {
                case 1:
                    activity_meat.setEnabled(true);
                    activity_vegetarian.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    activity_meat.setVisibility(View.VISIBLE);
                    activity_vegetarian.setEnabled(true);
                    break;
                case 3:
                    activity_meat.setEnabled(true);
                    activity_vegetarian.setEnabled(true);
                    break;
            }
        } catch (Exception e) {

        }

        activity_grade.setEnabled(true);
        activity_phone.setEnabled(true);
        activity_limit.setEnabled(true);
        activity_registration_deadline.setEnabled(true);
        activity_position.setEnabled(true);
        activity_start_date.setEnabled(true);
        activity_end_date.setEnabled(true);
        activity_tag.setEnabled(true);
        activity_price.setEnabled(true);
    }


    /**
     * when date picker set will excute
     *
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), f4_2_3.this, hour, minute, true);
        timePickerDialog.show();
    }

    /**
     * when time picker set will excute
     *
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //set time to calendar
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        myCalendar.set(Calendar.SECOND, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.TAIWAN);
        switch (who_invoke_timepicker) {
            case 0:
                //invoke by start date
                activity_start_date.setText(dateFormat.format(myCalendar.getTime()));
                break;
            case 1:
                //invoke by end date
                activity_end_date.setText(dateFormat.format(myCalendar.getTime()));
                break;
            case 2:
                //invoke by deadline date
                activity_registration_deadline.setText(dateFormat.format(myCalendar.getTime()));
                break;
        }

        who_invoke_timepicker = 0;//reset

    }

}