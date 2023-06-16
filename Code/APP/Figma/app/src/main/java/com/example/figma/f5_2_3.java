package com.example.figma;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f5_2_3 extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private static Gson gson;
    ImageButton revise_question_back;
    Button revise_question_upload_image;
    Button complete_revise_question;
    EditText surveyTitle;
    EditText surveyLink;
    EditText surveyDescription;
    EditText surveyPeopleLimit;
    EditText surveydeadlineDate;
    View view;
    Boolean revise = false;

    Question question;
    final Calendar myCalendar = Calendar.getInstance();//use in date picker
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f5_2_3() {
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
    public static f5_2_3 newInstance(String param1, String param2) {
        f5_2_3 fragment = new f5_2_3();
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
        Log.d("F523","onCreateView");
        view = inflater.inflate(R.layout.f5_2_3, container, false);
        Bundle get = getArguments();
        initialization();


        if (get != null && get.containsKey("question")) {
            question = getGsonParser().fromJson(get.getString("question"), Question.class);
            revise = true;
            ReviseData();
        }
        // Inflate the layout for this fragment
        surveydeadlineDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), f5_2_3.this, year, month, day);
            datePickerDialog.show();
        });

        revise_question_back = view.findViewById(R.id.QuestionReviseBackImageButton);
        revise_question_back.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_2_3_to_f5_2));

        revise_question_upload_image = view.findViewById(R.id.QuestionReviseUploadPictureButton);
        revise_question_upload_image.setOnClickListener(v -> {
            Log.d("F523", "上傳圖片");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 21);

        });

        complete_revise_question = view.findViewById(R.id.CompleteQuestionReviseButton);
        complete_revise_question.setOnClickListener(v -> {
            //跳轉到預覽頁面 帶著question過去
            Bundle data = new Bundle();
            question.setTitle(surveyTitle.getText().toString());
            question.setDescription(surveyDescription.getText().toString());
            question.setEndDate(surveydeadlineDate.getText().toString());
            question.setPeopleNumLimit(Integer.parseInt(surveyPeopleLimit.getText().toString()));
            question.setUrl(surveyLink.getText().toString());
            String questionJson = getGsonParser().toJson(question);
            data.putString("question",questionJson);
            Navigation.findNavController(v).navigate(R.id.action_f5_2_3_to_f5_1_2,data);
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

    public void initialization(){
        surveyTitle = view.findViewById(R.id.f521surveytitle);
        surveyLink = view.findViewById(R.id.f521surveyURL);
        surveyDescription = view.findViewById(R.id.f521surveyDescription);
        surveyPeopleLimit = view.findViewById(R.id.f521surveyLimit);
        surveydeadlineDate = view.findViewById(R.id.f523deadlinedate);
    }



    public void ReviseData() {
        surveyTitle.setText(question.getTitle());
        surveyDescription.setText(question.getDescription());
        surveyLink.setText(question.getUrl());
        surveyPeopleLimit.setText(question.getPeopleNumLimit().toString());
        surveydeadlineDate.setText(question.getEndDate());
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), f5_2_3.this, hour, minute, true);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.TAIWAN);
        surveydeadlineDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}


