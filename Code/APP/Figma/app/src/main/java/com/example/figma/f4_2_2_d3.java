package com.example.figma;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

public class f4_2_2_d3 extends Fragment {
    private static Gson gson;
    TextView kick_applicant;
    Button confirm_kick;
    Button cancel_kick;
    Button confirm;

    Event getevent;
    Personal getpersonal;
    Ticket getticket;

    String reason;
    String uid;
    String from;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f4_2_2_d3() {
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
    public static f4_2_2_d3 newInstance(String param1, String param2) {
        f4_2_2_d3 fragment = new f4_2_2_d3();
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
        View view = inflater.inflate(R.layout.f4_2_2_d3, container, false);
        // Inflate the layout for this fragment
        Bundle get = getArguments();
        //TODO:依照丟過來的資料判斷是要刪除活動還是要踢掉參加者
        kick_applicant = view.findViewById(R.id.KickApplicantTextView);

        confirm_kick = view.findViewById(R.id.ConfirmKickApplicantButton);
        confirm_kick.setOnClickListener(v -> {
            if(from.equals("activity")){
                try {
                    CancelActivity();
                    Navigation.findNavController(v).navigate(R.id.action_f4_2_2_d3_to_f4_2_1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if(from.equals("exchangelist")){
                try {
                    use();
                    Navigation.findNavController(v).navigate(R.id.action_f4_2_2_d3_to_f6_1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    KickMember();
                    Navigation.findNavController(v).navigate(R.id.action_f4_2_2_d3_to_f4_2_2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        });
        cancel_kick = view.findViewById(R.id.CancelKickApplicantButton);
        cancel_kick.setOnClickListener(v -> {
            if(from.equals("activity")){
                Navigation.findNavController(v).navigate(R.id.action_f4_2_2_d3_to_f4_2_1);

            }
            else if(from.equals("exchangelist")){
                Navigation.findNavController(v).navigate(R.id.action_f4_2_2_d3_to_f6_1);
            }
            else {
                Navigation.findNavController(v).navigate(R.id.action_f4_2_2_d3_to_f4_2_2);
            }
        });
        confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(v -> {
            if(from.equals("lotterylist")){
                Navigation.findNavController(v).navigate(R.id.action_f4_2_2_d3_to_f6_1);

            }
            else if(from.equals("exchangelist")){
                Navigation.findNavController(v).navigate(R.id.action_f4_2_2_d3_to_f6_1);
            }
        });
        confirm.setVisibility(View.INVISIBLE);

        from = get.getString("from");
        getevent = getGsonParser().fromJson(get.getString("event"),Event.class);
        switch (from) {
            case "activity":
                reason = get.getString("reason");
                kick_applicant.setText("確定要刪除 " + getevent.getEvent_name() + " 這個活動嗎?");
                confirm_kick.setText("確認刪除");
                confirm_kick.setVisibility(View.VISIBLE);
                cancel_kick.setVisibility(View.VISIBLE);

                break;
            case "lotterylist":
                if (get.containsKey("get")) {
                    confirm_kick.setVisibility(View.INVISIBLE);
                    cancel_kick.setVisibility(View.INVISIBLE);
                    confirm.setVisibility(View.VISIBLE);
                    if (get.getString("get").equals("yes")) {
                        kick_applicant.setText("恭喜中獎" + "\n" + "請至兌換頁面確認");
                    } else if (get.getString("get").equals("no")) {
                        kick_applicant.setText("沒抽中呢" + "\n" + "請再接再厲");
                    } else {
                        kick_applicant.setText("失敗" + "\n" + "此抽獎已超過時間");
                    }
                } else {
                    confirm_kick.setVisibility(View.INVISIBLE);
                    cancel_kick.setVisibility(View.INVISIBLE);
                    confirm.setVisibility(View.VISIBLE);
                    kick_applicant.setText("詳細規範" + "\n" + "1.此卷僅限於台科使用");
                    confirm_kick.setText("確認兌換");
                }

                break;
            case "exchangelist":
                //兌換
                if (get.containsKey("exchange")) {
                    getticket = getGsonParser().fromJson(get.getString("exchange"), Ticket.class);
                    confirm_kick.setVisibility(View.VISIBLE);
                    cancel_kick.setVisibility(View.VISIBLE);
                    confirm.setVisibility(View.INVISIBLE);
                    confirm_kick.setText("確認兌換");
                    kick_applicant.setText("此頁面僅供給商家使用" + "\n" + "確認要兌換" + "\n" + getticket.getTitle() + "嗎?");

                } else { //使用規範
                    confirm_kick.setVisibility(View.INVISIBLE);
                    cancel_kick.setVisibility(View.INVISIBLE);
                    confirm.setVisibility(View.VISIBLE);
                    kick_applicant.setText("詳細規範" + "\n" + "1.此卷僅限於台科使用");
                }
                break;
            default:
                getpersonal = getGsonParser().fromJson(get.getString("personal"), Personal.class);
                reason = get.getString("reason");
                uid = getpersonal.getPersonal_number();
                kick_applicant.setText("確定要剔除 " + getpersonal.getPersonal_name() + " 嗎?");
                confirm_kick.setText("確認剔除");
                confirm_kick.setVisibility(View.VISIBLE);
                cancel_kick.setVisibility(View.VISIBLE);

                break;
        }

        return view;
    }
    public Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }

    public void CancelActivity() throws JSONException {
        JSONObject data = new JSONObject();
        data.put("activityId",getevent.getEvent_id());
        data.put("requestUserId",Global.studentid);
        data.put("reason",reason);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),data.toString());
        Log.d("Cancel Activity data","0" + data.toString());
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .CancelActivity(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void KickMember() throws JSONException {
        JSONObject data = new JSONObject();
        data.put("activityId",getevent.getEvent_id());
        data.put("userId",uid);
        data.put("requestUserId",Global.studentid);
        data.put("reason",reason);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),data.toString());

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .KickActivityMember(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void use() throws JSONException {
        Call<RequestBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .useMyLottery(getticket.getHashSequence());
        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {

            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {

            }


        });
    }
}

