package com.example.figma;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.api.RetrofitClient;
import com.example.figma.api.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
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
public class f3_1 extends Fragment {
    Button add_friend, friendApplyList_btn, friendList_btn;
    ImageView personal_setting;
    ImageView personal_to_lottery;
    ImageView personal_to_question;
    ImageView personal_to_main;
    ImageView personal_to_event;
    ImageView personal;
    ImageView personal_to_chat;
    ImageView personal_to_notification;
    TextView personalName, tag;
    ConstraintLayout logout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SharedPreferences sharedPreferences;
    private ArrayList<Integer> friend_id_array;
    private RecyclerView friend_list_recyclerView;
    private FriendListRecycleAdapter friend_list_recycleAdapter;
    private FriendApplyListRecycleAdapter friendApplyListRecycleAdapter;

    private List<Friend> friendList = new ArrayList<>();
    private TextView friend_count;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f3_1() {
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
    public static f3_1 newInstance(String param1, String param2) {
        f3_1 fragment = new f3_1();
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
        View view = inflater.inflate(R.layout.f3_1, container, false);
        // Inflate the layout for this fragment
        add_friend = view.findViewById(R.id.AddFriendButton);
        personalName = view.findViewById(R.id.PersonalNameTextView);
        tag = view.findViewById(R.id.PersonalTag1TextView);
        friend_count = view.findViewById(R.id.textView8);

        //點擊好友列刷新
        friendList_btn = view.findViewById(R.id.FriendList);
        friendList_btn.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.f3_1));
        //--- 登出按鍵 ---
        logout = view.findViewById(R.id.logout);
        sharedPreferences = this.getActivity().getSharedPreferences("user_pred", Context.MODE_PRIVATE);
        logout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Navigation.findNavController(v).navigate(R.id.action_f3_1_to_f1_1);
        });
        //---        ---

        personalName.setText(Global.studentid+" / "+Global.name);
//        tag.setText(Global.tag);
        tag.setText(Global.convertToTag());
        add_friend.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_to_f3_1_1));
        personal_setting = view.findViewById(R.id.PersonalSetting);
        personal_setting.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_to_f3_3));

        personal_to_notification = view.findViewById(R.id.PersonalToNotification);
        personal_to_notification.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_to_f9));
        personal_to_chat = view.findViewById(R.id.PersonalToChat);
        personal_to_chat.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_to_f8));
        personal_to_lottery = view.findViewById(R.id.PersonalToLottery);
        personal_to_lottery.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_to_f6_1));
        personal_to_question = view.findViewById(R.id.PersonalToQuestion);
        personal_to_question.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_to_f5_1));
        personal_to_main = view.findViewById(R.id.PersonalToMain);
        personal_to_main.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_to_f2_1));
        personal_to_event = view.findViewById(R.id.PersonalToEvent);
        personal_to_event.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_1_to_f4_1));
        personal = view.findViewById(R.id.PersonalImageView);

        //--- 下載頭像 ---
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fileName", Global.image_path)
                .build();
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .image_user_download(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//              download
                FileOutputStream fos = null;
                try {
                    fos = getActivity().openFileOutput(Global.image_path, Context.MODE_PRIVATE);

                    fos.write(response.body().bytes());
                    String FILE_NAME =Global.image_path;
                    System.out.println("Saved to " + getActivity().getFilesDir() + "/" + FILE_NAME);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    // 取得並讀取個人照片
                    File imgFile = new File(getActivity().getFilesDir()+"/"+Global.image_path);
                    System.out.println("System : imgFile.exists");
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    // 將圖片轉為圓形
                    int width = personal.getWidth();
                    int height = personal.getHeight();
                    int r = personal.getHeight();
                    Bitmap backgroundBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(backgroundBmp);
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    RectF rect = new RectF(0, 0, r, r);
                    canvas.drawRoundRect(rect,r/2,r/2,paint);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    canvas.drawBitmap(myBitmap, null, rect, paint);

                    personal.setImageBitmap(backgroundBmp);
                }catch (Exception e){
                    System.out.println("System : imgFile.notexists."+e);
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });

        //--- RecycleView 設置 ---
        friend_list_recyclerView = view.findViewById(R.id.recyclerView);
        friend_list_recycleAdapter = new FriendListRecycleAdapter(getActivity(),friendList);
        friend_list_recyclerView.setAdapter(friend_list_recycleAdapter);
        friend_list_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //--- 取得好友id --
        Call<ResponseBody> call_friendid = RetrofitClient
                .getInstance()
                .getAPI()
                .get_friendlist(new User(Global.email, Global.password));
        call_friendid.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = "";
                friend_id_array = new ArrayList<>();
                try {
                    if (response.body()!=null)s = response.body().string();
                } catch (IOException e) {
                    System.out.println("Get response.body Fail");
                }
                JSONArray jsonArray = null;
                JSONObject jsonObject = null;
                try {
                    jsonArray = new JSONArray(s);
                    int array_length = jsonArray.length();
                    System.out.println(array_length);

                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject = new JSONObject(jsonArray.get(i).toString());
                        friend_id_array.add(Integer.parseInt(jsonObject.getString("idfriend")));
                    }
                    System.out.println(friend_id_array);
//                    System.out.println(jsonArray.get(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //--- 讀取好友公開資料 --
                friend_id_array.forEach((i) -> {
                    friend_count.setText("在活動中認識了 "+friend_id_array.size()+" 個朋友");
                    RequestBody requestBody_get_user_public = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("iduser", String.valueOf(i))
                            .build();
                    Call<ResponseBody> call_get_user_public = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .get_user_public(requestBody_get_user_public);
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
                                File imgFile = new File(getActivity().getFilesDir()+"/"+tempJsonObject.getString("image_path"));
                                downloadImage(tempJsonObject.getString("image_path"));
                                friendList.add(new Friend(
                                        i,
                                        tempJsonObject.getString("name"),
                                        tempJsonObject.getString("student_id"),
                                        tempJsonObject.getString("tag"),
                                        tempJsonObject.getString("image_path"),
                                        tempJsonObject.getString("school"),
                                        tempJsonObject.getBoolean("gender")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            friend_list_recycleAdapter.setTasks(friendList);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                        }
                    });
                });
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Fail : "+t);
            }

        });

        friendApplyList_btn = view.findViewById(R.id.FriendApplyList);
        friendApplyList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendApplyListRecycleAdapter = new FriendApplyListRecycleAdapter(getActivity(),friendList);
                friend_list_recyclerView.setAdapter(friendApplyListRecycleAdapter);
                friendList = new ArrayList<>() ;

                //--- 取得好友id --
                Call<ResponseBody> call_friendApplyid = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .get_friend_apply(new User(Global.email, Global.password));
                call_friendApplyid.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String s = "";
                        friend_id_array = new ArrayList<>();
                        try {
                            if (response.body()!=null)s = response.body().string();
                        } catch (IOException e) {
                            System.out.println("Get response.body Fail");
                        }
                        JSONArray jsonArray = null;
                        JSONObject jsonObject = null;
                        try {
                            jsonArray = new JSONArray(s);
                            int array_length = jsonArray.length();

                            for (int i=0;i<jsonArray.length();i++){
                                jsonObject = new JSONObject(jsonArray.get(i).toString());
                                friend_id_array.add(Integer.parseInt(jsonObject.getString("iduser")));
                            }
                            System.out.println(friend_id_array);
//                    System.out.println(jsonArray.get(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //--- 讀取好友公開資料 --
                        friend_id_array.forEach((i) -> {
                            RequestBody requestBody_get_user_public = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("iduser", String.valueOf(i))
                                    .build();
                            Call<ResponseBody> call_get_user_public = RetrofitClient
                                    .getInstance()
                                    .getAPI()
                                    .get_user_public(requestBody_get_user_public);
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
                                        File imgFile = new File(getActivity().getFilesDir()+"/"+tempJsonObject.getString("image_path"));
                                        downloadImage(tempJsonObject.getString("image_path"));
                                        friendList.add(new Friend(
                                                i,
                                                tempJsonObject.getString("name"),
                                                tempJsonObject.getString("student_id"),
                                                tempJsonObject.getString("tag"),
                                                tempJsonObject.getString("image_path"),
                                                tempJsonObject.getString("school"),
                                                tempJsonObject.getBoolean("gender")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    friendApplyListRecycleAdapter.setTasks(friendList);
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                                }
                            });
                        });
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("Fail : "+t);
                    }

                });
                friendApplyListRecycleAdapter.setTasks(friendList);
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        friendList = new ArrayList<>() ;
    }

    public void downloadImage(String image_path){
        //--- 下載頭像 ---
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fileName", image_path)
                .build();
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .image_user_download(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//              download
                FileOutputStream fos = null;
                try {
                    fos = getActivity().openFileOutput(image_path, Context.MODE_PRIVATE);

                    fos.write(response.body().bytes());
//                    String FILE_NAME =image_path;
                    System.out.println("Saved to " + getActivity().getFilesDir() + "/" + image_path);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                File imgFile = new File(getActivity().getFilesDir()+"/"+image_path);
                if (imgFile.exists()){
                    System.out.println("System : imgFile.exists");
                }else {
                    System.out.println("System : imgFile.notexists");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
//        return null;
    }
}