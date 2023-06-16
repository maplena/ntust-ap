package com.example.figma;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
public class f4_3_1_1 extends Fragment {
    private static Gson gson;
    ImageButton end_post_back;
    RecyclerView end_post_people_list;
    EndPostEventPeopleListAdapter end_post_event_people_list_adapter;
    List<Personal> past_join;
    Integer page = 0;
    Event getevent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_3_1_1() {
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
    public static f4_3_1_1 newInstance(String param1, String param2) {
        f4_3_1_1 fragment = new f4_3_1_1();
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
        past_join = new ArrayList<>();
        Bundle get = getArguments();
        getevent = getGsonParser().fromJson(get.getString("event"),Event.class);
        getData(getevent.getEvent_id());
        end_post_event_people_list_adapter = new EndPostEventPeopleListAdapter(getActivity());
        end_post_event_people_list_adapter.setTasks(past_join);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f4_3_1_1, container, false);
        // Inflate the layout for this fragment
        page = 0;

        end_post_back = view.findViewById(R.id.EndPostPeopleListImageButton);
        end_post_back.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_3_1_1_to_f4_2_1));
        end_post_people_list = view.findViewById(R.id.EndPostPeopleListRecyclerView);
        end_post_people_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        end_post_people_list.setAdapter(end_post_event_people_list_adapter);


        end_post_people_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)){
                    page += 1;
                    getData(getevent.getEvent_id());
                    recyclerView.post(() -> end_post_event_people_list_adapter.notifyDataSetChanged());
                }

            }
        });
        return view;
    }
    public void getData(Integer aid){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getPastActivityMember(aid,page);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = "";
                String data = "";

                try {
                    if (response.body()!=null)s = response.body().string();
                } catch (IOException e) {

//                            e.printStackTrace();
                }
                try {
                    JSONArray alldata = new JSONArray(s);//總共的列表
                    for (int i = 0; i < alldata.length(); i++) {
                        JSONObject object = alldata.getJSONObject(i);
                        Iterator<String> it = object.keys();

                        while (it.hasNext()) {
                            String key = it.next();
                            Object o_1 = object.get(key);
                            if(key.equals("userId"))data = o_1.toString();
                        }
                        Global.tempF7_studentid = data;
                        getJoinData();
                        data = "";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getJoinData(){
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
                Bitmap image = null;
                try {
                    if (response.body()!=null)s = response.body().string();
                } catch (IOException e) {
                    System.out.println("Get response.body Fail");
                }
                System.out.println(s);

                try {
                    JSONObject tempJsonObject = new JSONObject(s);
                    File imgFile = new File(getActivity().getFilesDir()+"/"+tempJsonObject.getString("image_path"));
                    if (imgFile.exists()){
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
                        image = backgroundBmp;


                    }else {
                        System.out.println("System : imgFile.notexists");
                    }
                    Personal personal = new Personal(image,Global.tempF7_studentid,tempJsonObject.getString("name"),tempJsonObject.getString("tag"),tempJsonObject.getBoolean("gender"));
                    past_join.add(personal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("F4311","name:"+past_join.get(0).getPersonal_name());
                end_post_event_people_list_adapter.setTasks(past_join);
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    public static Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }
}

