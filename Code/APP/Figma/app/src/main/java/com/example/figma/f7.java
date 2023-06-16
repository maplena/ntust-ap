package com.example.figma;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.figma.api.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class f7 extends Fragment {
    Button sex_man;
    Button sex_woman;
    Button sex_other;
    Button relation_single;
    Button relation_romantic;
    Button relation_other;
    ImageButton return_btn;

    ImageView other_personal;
    ImageView other_personal_to_lottery;
    ImageView other_personal_to_question;
    ImageView other_personal_to_main;
    ImageView other_personal_to_event;
    ImageView other_personal_to_personal;

    EditText introduce;
    EditText interest;

    TextView personal_studentid, personal_tag;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public f7() {
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
    public static f7 newInstance(String param1, String param2) {
        f7 fragment = new f7();
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
        View view = inflater.inflate(R.layout.f7, container, false);
        // Inflate the layout for this fragment
        sex_man = view.findViewById(R.id.FriendList);
        sex_woman = view.findViewById(R.id.FriendApplyList);
        sex_other = view.findViewById(R.id.SexOther);
        relation_single = view.findViewById(R.id.RelationSingle);
        relation_romantic = view.findViewById(R.id.RelationRomantic);
        relation_other = view.findViewById(R.id.RelationOther);
        introduce = view.findViewById(R.id.OtherPersonalIntroduceEditText);
        interest = view.findViewById(R.id.OtherPersonalInterestEditText);
        other_personal = view.findViewById(R.id.OtherPersonalImageView);
        personal_studentid = view.findViewById(R.id.PersonalStudentId);
        personal_tag  =view.findViewById(R.id.f4211tag1);

        return_btn = view.findViewById(R.id.ReturnBtn);
        return_btn.setOnClickListener(view1 -> getActivity().onBackPressed());

        other_personal_to_lottery = view.findViewById(R.id.OtherPersonalToLottery);
        other_personal_to_lottery.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f7_to_f6_1));
        other_personal_to_question = view.findViewById(R.id.OtherPersonalToQuestion);
        other_personal_to_question.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f7_to_f5_1));
        other_personal_to_main = view.findViewById(R.id.OtherPersonalToMain);
        other_personal_to_main.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f7_to_f2_1));
        other_personal_to_event = view.findViewById(R.id.OtherPersonalToEvent);
        other_personal_to_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f7_to_f4_1));

        other_personal_to_personal = view.findViewById(R.id.OtherPersonalToPersonal);
        other_personal_to_personal.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f7_to_f3_1));

        //下載該 id 公開資料
        RequestBody requestBody_get_user_public = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("studentid", Global.tempF7_studentid)
                .build();
        Call<ResponseBody> call_get_user_public = RetrofitClient
                .getInstance()
                .getAPI()
                .get_user_public_bystudentid(requestBody_get_user_public);
        call_get_user_public.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = "";
                try {
                    if (response.body()!=null)s = response.body().string();
                } catch (IOException e) {
                    System.out.println("Get response.body Fail");
                }
                System.out.println(s);

                try {
                    JSONObject tempJsonObject = new JSONObject(s);
                    try {
                        File imgFile = new File(getActivity().getFilesDir()+"/"+tempJsonObject.getString("image_path"));

                        System.out.println("System : imgFile.exists");
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        // 將圖片轉為圓形
                        int width = 245;
                        int height = 245;
                        int r = 245;
                        Bitmap backgroundBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(backgroundBmp);
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        RectF rect = new RectF(0, 0, r, r);
                        canvas.drawRoundRect(rect,r/2,r/2,paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(myBitmap, null, rect, paint);

                        other_personal.setImageBitmap(backgroundBmp);
                    }catch (Exception e){
                        System.out.println("System : imgFile.notexists");
                    }

                    personal_studentid.setText(Global.tempF7_studentid + " / " + tempJsonObject.getString("name"));
                    introduce.setText(tempJsonObject.getString("self_info"));
                    interest.setText(tempJsonObject.getString("intrest"));
                    personal_tag.setText(Global.temp_convertToTag(
                            tempJsonObject.getString("school"),
                            tempJsonObject.getBoolean("gender"),
                            Global.tempF7_studentid));

                    boolean gender = tempJsonObject.getBoolean("gender");
                    if (gender){
                        sex_man.setBackgroundResource(R.drawable.button_style13);
                        sex_woman.setBackgroundResource(R.drawable.button_style2);
                    }else {
                        sex_man.setBackgroundResource(R.drawable.button_style2);
                        sex_woman.setBackgroundResource(R.drawable.button_style13);
                    }

                    int relationship = tempJsonObject.getInt("relationship");
                    if (relationship == 1){
                        relation_single.setBackgroundResource(R.drawable.button_style14);
                        relation_romantic.setBackgroundResource(R.drawable.button_style6);
                        relation_other.setBackgroundResource(R.drawable.button_style6);
                    }else if (relationship == 2){
                        relation_single.setBackgroundResource(R.drawable.button_style6);
                        relation_romantic.setBackgroundResource(R.drawable.button_style14);
                        relation_other.setBackgroundResource(R.drawable.button_style6);
                    }else if (relationship == 3){
                        relation_single.setBackgroundResource(R.drawable.button_style6);
                        relation_romantic.setBackgroundResource(R.drawable.button_style6);
                        relation_other.setBackgroundResource(R.drawable.button_style6);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
        return view;
    }
}
