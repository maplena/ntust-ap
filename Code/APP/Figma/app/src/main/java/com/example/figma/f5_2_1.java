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
import android.widget.TimePicker;

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
public class f5_2_1 extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private static Gson gson;
    ImageButton post_question_back;
    Button post_question;
    Button upload_question_picture;

    EditText surveyTitleText;
    EditText surveyURLText;
    EditText surveyDescriptionText;
    EditText surveyLimitText;
    EditText surveyDeadLine;
    final Calendar myCalendar = Calendar.getInstance();//use in date picker
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f5_2_1() {
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
    public static f5_2_1 newInstance(String param1, String param2) {
        f5_2_1 fragment = new f5_2_1();
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
        View view = inflater.inflate(R.layout.f5_2_1, container, false);
        // Inflate the layout for this fragment
        surveyTitleText = view.findViewById(R.id.f521surveytitle);
        surveyURLText = view.findViewById(R.id.f521surveyURL);
        surveyDescriptionText = view.findViewById(R.id.f521surveyDescription);
        surveyDeadLine = view.findViewById(R.id.f521surveyDeadLineTime);
        surveyDeadLine.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), f5_2_1.this, year, month, day);
            datePickerDialog.show();
        });

        surveyLimitText = view.findViewById(R.id.f521surveyLimit);


        post_question_back = view.findViewById(R.id.PostQuestionBackImageButton);
        post_question_back.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_2_1_to_f5_2));
        post_question = view.findViewById(R.id.QuestionPostButton);
        post_question.setOnClickListener(v -> {
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

                JSONObject data = new JSONObject();
                data.put("title", surveyTitleText.getText().toString());
                data.put("url", surveyURLText.getText().toString());
                data.put("description", surveyDescriptionText.getText().toString());
                data.put("image_path", "image_path");

                data.put("host", Global.name);
                data.put("hostID", Global.studentid);
                data.put("host_email", Global.email);
                data.put("host_phone_number", "0912345678");

                data.put("tag", "無");
                data.put("school_limit", 0);
                data.put("gender", 0);
                data.put("people_num_limit", Integer.parseInt(surveyLimitText.getText().toString()));
                data.put("grade", 0);
                Date start_date = new Date();
                //Date end_date = new Date(start_date.getTime() + 2592000000L);
                SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.TAIWAN);
                data.put("start_date", formater.format(start_date));
                //data.put("end_date", formater.format(end_date));
                data.put("end_date", surveyDeadLine.getText().toString());
                data.put("status", "running");
                Log.d("F521","JSON :"+data.toString());
                RequestBody request =
                        RequestBody.create(MediaType.parse("application/json"), data.toString());
                RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"), f);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("img", f.getName(), fbody);

                AddSurvey(request,body);
            } catch (Exception exception) {
                exception.printStackTrace();
            }


            Navigation.findNavController(v).navigate(R.id.action_f5_2_1_to_f5_2);
        });
        upload_question_picture = view.findViewById(R.id.QuestionUploadPictureButton);
        upload_question_picture.setOnClickListener(v -> {
            Log.d("F521", "上傳圖片");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 21);
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

    public void AddSurvey(RequestBody request, MultipartBody.Part img) {
        Log.d("F521 AddActivity", "send");
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .addSurvey(request, img);
        Log.d("F521 AddActivity", "had send");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("F521 onResponse", "receive");
                String s = "";
                try {
                    if (response.isSuccessful()) {
                        s = response.body().string();
                        Log.d("F521 onResponse status", "receive" + s);
                        Global.bitmap = null;
                    } else Log.d("F521 onResponse status", "error");
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), f5_2_1.this, hour, minute, true);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.TAIWAN);
        surveyDeadLine.setText(dateFormat.format(myCalendar.getTime()));
    }

}
