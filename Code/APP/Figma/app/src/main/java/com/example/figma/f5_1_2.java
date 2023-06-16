package com.example.figma;

import android.graphics.Bitmap;
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

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
public class f5_1_2 extends Fragment {
    private static Gson gson;
    ImageButton confirm_post_question_back;
    Button back_to_revise_question;
    Button confirm_post_question;
    Question question;
    View view;

    TextView pageTitle;
    TextView surveyTitle;
    ImageView surveyImageView;
    TextView surveyDescription;
    TextView OtherPersonalTag3TextView;
    boolean revise=false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f5_1_2() {
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
    public static f5_1_2 newInstance(String param1, String param2) {
        f5_1_2 fragment = new f5_1_2();
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
        view = inflater.inflate(R.layout.f5_1_2, container, false);
        // Inflate the layout for this fragment
        initialization();
        Bundle get = getArguments();
        //如果有bundle
        if (get != null && get.containsKey("question")) {
            question = getGsonParser().fromJson(get.getString("question"), Question.class);
            revise = true;
            ReviseData();
        }
        if(revise)pageTitle.setText("發布問卷");
        else pageTitle.setText("修改問卷");
        surveyImageView.setImageBitmap(Global.bitmap);
        confirm_post_question_back = view.findViewById(R.id.ConfirmQuestionPostBackImageButton);
        confirm_post_question_back.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f5_1_2_to_f5_2));
        back_to_revise_question = view.findViewById(R.id.ConfirmQuestionPostBackReviseButton);
        back_to_revise_question.setOnClickListener(v -> {
            //TODO:判斷是從5_1_1還是5_2_3來的,分別回到對應的地方
            Navigation.findNavController(v).navigate(R.id.action_f5_1_2_to_f5_2_1);
        });
        confirm_post_question = view.findViewById(R.id.ConfirmQuestionPostButton);
        confirm_post_question.setOnClickListener(v -> {
            //TODO:判斷是從5_1_1還是5_2_3來的,名子分別改成確認發布/確認修改
            //送出請求
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
                data.put("title", question.getTitle());
                data.put("url", question.getUrl());
                data.put("description", question.getDescription());
                data.put("image_path", "image_path");

                data.put("host", Global.name);
                data.put("hostID", Global.studentid);
                data.put("host_email", Global.email);
                data.put("host_phone_number", "0912345678");

                data.put("tag", "無");
                data.put("school_limit", 0);
                data.put("gender", 0);
                data.put("people_num_limit", question.getPeopleNumLimit());
                data.put("grade", 0);
                Date start_date = new Date();
                SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.TAIWAN);
                data.put("start_date", formater.format(start_date));
                data.put("end_date", question.getEndDate());
                data.put("status", "running");
                data.put("id",question.getId());
                data.put("requestUserId",Global.studentid);
                Log.d("F512","JSON :"+data.toString());
                RequestBody request =
                        RequestBody.create(MediaType.parse("application/json"), data.toString());
                RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"), f);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("img", f.getName(), fbody);

                EditSurvey(request,body);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            Navigation.findNavController(v).navigate(R.id.action_f5_1_2_to_f5_2);
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
        pageTitle =view.findViewById(R.id.f512pagetitle);
        surveyTitle = view.findViewById(R.id.f4211tag1);
        surveyImageView = view.findViewById(R.id.surveyImageView);
        surveyDescription = view.findViewById(R.id.surveyDescription);
        OtherPersonalTag3TextView = view.findViewById(R.id.OtherPersonalTag3TextView);
    }
    public void ReviseData() {
        surveyTitle.setText(question.getTitle());
        surveyDescription.setText(question.getDescription());
        String limit = "人數限制:"+question.getPeopleNumLimit().toString()+"人\n截止時間"+question.DateToSimpleString(question.getEndDate());
        OtherPersonalTag3TextView.setText(limit);
    }

    public void EditSurvey(RequestBody request, MultipartBody.Part img) {
        Log.d("F521 AddActivity", "send");
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .UpdateSurvey(request, img);
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

                    } else Log.d("F521 onResponse status", "error");
                    //f (response.body() != null) s = response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Global.bitmap = null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}


