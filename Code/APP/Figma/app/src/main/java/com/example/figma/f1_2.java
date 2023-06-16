package com.example.figma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.figma.api.RegistrationUser;
import com.example.figma.api.RetrofitClient;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f1_2 extends Fragment {
    Button register_confirm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String student_id[],school;
    private EditText email,name,password,passwordCheck;
    private TextView infoTextView;
    private Button maleBtn,femaleBtn;
    private boolean gender = true;
    private boolean legal = true;
    public f1_2() {
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
    public static f1_2 newInstance(String param1, String param2) {
        f1_2 fragment = new f1_2();
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
        View view = inflater.inflate(R.layout.f1_2, container, false);
        email = view.findViewById(R.id.editText);
        name = view.findViewById(R.id.editText2);
        password = view.findViewById(R.id.editText3);
        passwordCheck = view.findViewById(R.id.editText4);
        infoTextView = view.findViewById(R.id.f4211tag1);
        register_confirm = view.findViewById(R.id.RegisterConfirmButton);
        maleBtn = view.findViewById(R.id.maleBtn);
        femaleBtn = view.findViewById(R.id.femaleBtn);
        register_confirm.setOnClickListener(v -> {
            register_confirm.setEnabled(false);
            legal=true;
            //--- 用戶學校偵測 ---
            String emailTemp = email.getText().toString();
            student_id = emailTemp.split("@");
            try {
                if (student_id[1].contains("ntust")){
                    school = "台科大";
                }else if (student_id[1].contains("ntu")){
                    school = "台大";
                }else if (student_id[1].contains("ntnu")){
                    school = "台師大";
                }else {
                    infoTextView.setText("請使用三校電子郵件註冊");
                    legal = false;
                }
            }catch (Exception e){
                infoTextView.setText("請使用三校電子郵件註冊");
                legal = false;
                e.printStackTrace();
            }
            if(name.getText().toString().equals("") || password.getText().toString().equals("") || passwordCheck.getText().toString().equals("")){
                infoTextView.setText("欄位不可為空");
                legal = false;
            }
            if (!(password.getText().toString().equals(passwordCheck.getText().toString())) && legal){
                infoTextView.setText("密碼確認錯誤");
                legal = false;
            }
            // --- 用戶學校偵測 --- end

            // --- registration ---
            if (legal) {
                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .registration(new RegistrationUser(
                                student_id[0],
                                email.getText().toString().trim(),
                                name.getText().toString().trim(),
                                password.getText().toString().trim(),
                                gender,
                                school));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String s = "";
                        String message = "";
                        if (response.isSuccessful()){
                            try {
                                if (response.body() != null) s = response.body().string();
                            } catch (IOException e) {
                                System.out.println("Exception : "+e);
                            }
                        }else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                System.out.println(jObjError.getString("message"));
                                infoTextView.setText(jObjError.getString("message"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        String token[] = s.split("-");
                        if (token.length == 5) {
                            Navigation.findNavController(v).navigate(R.id.action_f1_2_to_f1_1_2);
                        } else {
//                                System.out.println("token : "+s);
//                                System.out.println("message : "+message);
                                infoTextView.setText("email已被使用 請使用下列帳號註冊 \n @mail.ntust.edu.tw / @ntu.edu.tw / @ntnu.edu.tw ");
                        }
                        register_confirm.setEnabled(true);
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("Fail : " + t);
                        register_confirm.setEnabled(true);
                    }
                });
            }
            if (!legal)register_confirm.setEnabled(true);
        });

        maleBtn.setOnClickListener(v -> {
            maleBtn.setBackgroundResource(R.drawable.button_style1);
            femaleBtn.setBackgroundResource(R.drawable.button_style2);
            gender = true;
        });
        femaleBtn.setOnClickListener(v -> {
            maleBtn.setBackgroundResource(R.drawable.button_style2);
            femaleBtn.setBackgroundResource(R.drawable.button_style1);
            gender = false;
        });
        // Inflate the layout for this fragment
        return view;
    }

}