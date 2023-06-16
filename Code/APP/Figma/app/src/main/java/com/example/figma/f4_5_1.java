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

import com.example.figma.Eventtest.ParticipentItem;
import com.example.figma.api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class f4_5_1 extends Fragment {
    private static Gson gson;
    ImageButton people_list_back;
    RecyclerView event_people_list;
    EventPeopleListAdapter event_people_list_adapter;
    Event getevent;
    Integer page = 0;
    List<Personal> activity_personal;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_5_1() {
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
    public static f4_5_1 newInstance(String param1, String param2) {
        f4_5_1 fragment = new f4_5_1();
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
        View view = inflater.inflate(R.layout.f4_5_1, container, false);
        // Inflate the layout for this fragment
        page = 0;
        activity_personal = new ArrayList<>();
        people_list_back = view.findViewById(R.id.EventPeopleListBackImageButton);
        people_list_back.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f4_5_1_to_f4_5));
        Bundle get = getArguments();
        getevent = getGsonParser().fromJson(get.getString("event"), Event.class);
        getData(getevent.getEvent_id());
        //Log.d("f451 onCreate view getdata","data:"+getevent.getEvent_id());
        event_people_list = view.findViewById(R.id.EventPeopleListRecyclerView);
        event_people_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        event_people_list_adapter = new EventPeopleListAdapter(getActivity());
        event_people_list.setAdapter(event_people_list_adapter);
        event_people_list_adapter.setTasks(activity_personal);
        event_people_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1;
                    getData(getevent.getEvent_id());
                    recyclerView.post(() -> event_people_list_adapter.notifyDataSetChanged());
                }

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

    public void getData(Integer aid) {
        Call<List<ParticipentItem>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getActivityMember(aid, page);

        call.enqueue(new Callback<List<ParticipentItem>>() {
            @Override
            public void onResponse(Call<List<ParticipentItem>> call, Response<List<ParticipentItem>> response) {

                List<ParticipentItem> participentItemList = new LinkedList<>();
                try {
                    if (response.body() != null) {
                        //s = response.body().string();
                        participentItemList = response.body();
                    }
                } catch (Exception e) {

//                            e.printStackTrace();
                }
                try {
                    for (ParticipentItem participent : participentItemList) {
                        Global.tempF7_studentid = participent.getUserId();
                        getJoinData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<List<ParticipentItem>> call, Throwable t) {

            }
        });
    }

    public void getJoinData() {
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
                    if (response.body() != null) s = response.body().string();
                } catch (IOException e) {
                    Log.d("F451 Get response.body", "Fail");
                }
                System.out.println(s);

                try {
                    JSONObject tempJsonObject = new JSONObject(s);
                    File imgFile = new File(getActivity().getFilesDir() + "/" + tempJsonObject.getString("image_path"));
                    if (imgFile.exists()) {
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
                        canvas.drawRoundRect(rect, r / 2, r / 2, paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(myBitmap, null, rect, paint);
                        image = backgroundBmp;


                    } else {
                        System.out.println("F451 System : imgFile.notexists");
                    }
                    Personal personal = new Personal(image, tempJsonObject.getString("student_id"), tempJsonObject.getString("name"), tempJsonObject.getString("tag"), tempJsonObject.getBoolean("gender"));
                    activity_personal.add(personal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                event_people_list_adapter.setTasks(activity_personal);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

}
