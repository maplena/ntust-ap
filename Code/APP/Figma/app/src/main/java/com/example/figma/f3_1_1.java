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
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.api.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class f3_1_1 extends Fragment {

    ImageView toLottery;
    ImageView toQuestion;
    ImageView toHome;
    ImageView toEvent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView friend_apply_recyclerView;
    private FriendApplyRecycleAdapter friend_apply_recycleAdapter;
    private List<Friend> friendList = new ArrayList<>();


    public f3_1_1() {
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
    public static f3_1_1 newInstance(String param1, String param2) {
        f3_1_1 fragment = new f3_1_1();
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
        View view = inflater.inflate(R.layout.f3_1_1, container, false);
        // Inflate the layout for this fragment
        toLottery = view.findViewById(R.id.OtherPersonalToLottery);
        toLottery.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_1_to_f6_1));
        toQuestion = view.findViewById(R.id.OtherPersonalToQuestion);
        toQuestion.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_1_to_f5_1));
        toHome = view.findViewById(R.id.OtherPersonalToMain);
        toHome.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_1_to_f2_1));
        toEvent = view.findViewById(R.id.OtherPersonalToEvent);
        toEvent.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_1_to_f4_1));

        ImageView personal_imageView = view.findViewById(R.id.OtherPersonalImageView);
        try {
            File imgFile = new File("/data/user/0/com.example.figma/files"+"/"+Global.image_path);
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
            personal_imageView.setImageBitmap(backgroundBmp);
        }catch (Exception e){
            System.out.println("System : imgFile.notexists.");
        }

        TextView name = view.findViewById(R.id.PersonalStudentId);
        name.setText(Global.name);
        TextView studentId = view.findViewById(R.id.StudentId);
        studentId.setText(Global.studentid);

        friend_apply_recyclerView = view.findViewById(R.id.recyclerView);
        friend_apply_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        friend_apply_recycleAdapter = new FriendApplyRecycleAdapter(getActivity(),friendList);
        friend_apply_recyclerView.setAdapter(friend_apply_recycleAdapter);

        EditText editText = view.findViewById(R.id.editText);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                RequestBody requestBody_get_user_public = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("name", editText.getText().toString())
                        .build();

                Call<ResponseBody> call_get_user_public = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .get_user_public_byname(requestBody_get_user_public);

                call_get_user_public.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        friendList.clear();
                        String s = "";
                        try {
                            if (response.body()!=null)s = response.body().string();
                        } catch (IOException e) {
                            System.out.println("Get response.body Fail");
                        }
                        System.out.println(s);

                        try {
//                            JSONObject tempJsonObject = new JSONObject(s);
                            System.out.println(s);
                            JSONArray tempJsonArray = new JSONArray(s);
//                            JSONArray tempJsonArray = tempJsonObject.getJSONArray("");
                            System.out.println(tempJsonArray);
                            for (int i = 0;i < tempJsonArray.length();i++){

                                JSONObject tempJsonObject = tempJsonArray.getJSONObject(i);
                                File imgFile = new File(getActivity().getFilesDir()+"/"+tempJsonObject.getString("image_path"));
                                if (!tempJsonObject.getString("student_id").equals(null)){
                                    friendList.add(new Friend(
                                            tempJsonObject.getInt("iduser"),
                                            tempJsonObject.getString("name"),
                                            tempJsonObject.getString("student_id"),
                                            tempJsonObject.getString("tag"),
                                            tempJsonObject.getString("image_path"),
                                            tempJsonObject.getString("school"),
                                            tempJsonObject.getBoolean("gender")));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        friend_apply_recycleAdapter.setTasks(friendList);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                    }
                });
            }
            return false;
        });

        return view;
    }
}