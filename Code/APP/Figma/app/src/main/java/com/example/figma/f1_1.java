
        package com.example.figma;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.os.Handler;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.fragment.app.Fragment;
        import androidx.navigation.Navigation;

        import com.example.figma.api.RetrofitClient;
        import com.example.figma.api.User;

        import org.json.JSONException;
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
public class f1_1 extends Fragment {
    Button register,forgot_password, language,service,login;
    TextView email,password,loginOrNot,reg;
    ImageView serviceImage,languageImage;
    View divider;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="user_pred";
    private static final String USER_EMAIL="email";
    private static final String USER_PASSWORD="password";
    private static final String LOGIN_SUCESS="false";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CheckBox checkBox;
    public f1_1() {
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
    public static f1_1 newInstance(String param1, String param2) {
        f1_1 fragment = new f1_1();
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
//        // 跳過登入驗證
//        View view = inflater.inflate(R.layout.f1_1, container, false);
//        login = view.findViewById(R.id.LoginButton);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_f1_1_to_f2_1);
//            }
//        });
        View view = inflater.inflate(R.layout.f1_1, container, false);

        // Inflate the layout for this fragment
        divider = view.findViewById(R.id.view);
        reg = view.findViewById(R.id.SexOther);
        serviceImage = view.findViewById(R.id.imageView3);
        languageImage = view.findViewById(R.id.PersonalToChat);
        language = view.findViewById(R.id.LanguageButton);
        register = view.findViewById(R.id.RegisterButton);
        checkBox = view.findViewById(R.id.autologincheckbox);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_f1_1_to_f1_2);
            }
        });
        service = view.findViewById(R.id.ServiceButton);
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_f1_1_to_f1_1_4);
            }
        });
        forgot_password = view.findViewById(R.id.ForgotPasswordButton);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_f1_1_to_f1_1_1);
            }
        });
        login = view.findViewById(R.id.LoginButton);
        email=view.findViewById(R.id.editText);
        password=view.findViewById(R.id.editText2);
        loginOrNot=view.findViewById(R.id.textView2);
        setVisibility(View.INVISIBLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 作帳密驗證,使用學號及讚證碼的修改密碼登入
                // --- get_user_private ---
                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .checkUser(new User(email.getText().toString().trim(), password.getText().toString().trim()));

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String s = "";
                        try {
                            if (response.body()!=null){s = response.body().string();}
                        } catch (IOException e) {
//                            e.printStackTrace();
                        }

                        JSONObject jsonObject = null;
                        boolean loginSucess = false;
                        try {
                            jsonObject = new JSONObject(s);
                            if (jsonObject.getString("email").equals(email.getText().toString().trim())
                                    &&  jsonObject.getString("enabled").equals("true")) {
                                System.out.println("Login");
                                Global.email = email.getText().toString().trim();
                                Global.password = password.getText().toString().trim();
                                loginSucess=true;
                                Navigation.findNavController(v).navigate(R.id.action_f1_1_to_f2_1);
                                System.out.println(s);
                                Global.studentid = jsonObject.getString("studentid");
                                Global.name = jsonObject.getString("name");
                                Global.school = jsonObject.getString("school");
                                Global.tag = jsonObject.getString("tag");
                                Global.self_info = jsonObject.getString("self_info");
                                Global.image_path = jsonObject.getString("image_path");
                                Global.interest = jsonObject.getString("interest");
                                Global.relationship = jsonObject.getInt("relationship");
                                Global.gender = jsonObject.getBoolean("gender");

                                // 嘗試轉換學號
                                try {
                                    Global.school_num = Integer.parseInt(Global.school);
                                    if (Global.school.equals("1")){
                                        Global.userGrade = (11 - Integer.parseInt(Global.studentid.substring(1,3)));
                                    }else if (Global.school.equals("2")){
                                        Global.userGrade = (111 - Integer.parseInt(Global.studentid.substring(1,4)));
                                    }else if (Global.school.equals("3")){
                                        Global.userGrade = (11 - Integer.parseInt(Global.studentid.substring(1,3)));
                                    }

//                                    System.out.println(Global.school_num + " / " + Global.studentid.substring(1,4) + " / " + Global.userGrade);
                                }catch (Exception e){
                                    System.out.println("System : school_num or userGrade Global Failed");
                                }
                                if (checkBox.isChecked()){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(USER_EMAIL,Global.email);
                                    editor.putString(USER_PASSWORD,Global.password);
                                    editor.putString(LOGIN_SUCESS,"true");
                                    editor.apply();
                                }
                            }else {
                                loginSucess=true;
                                loginOrNot.setText(("尚未驗證"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(loginSucess==false){
                            setVisibility(View.VISIBLE);
                            loginOrNot.setText("登入失敗");
                            System.out.println("Login Fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        setVisibility(View.VISIBLE);
                        System.out.println("Fail : "+t);
                    }
                });
            }
        });
        //--- 自動登入 ---

        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String sucess = sharedPreferences.getString(LOGIN_SUCESS,null);
        try {
            if (sucess.equals("true")){
                email.setText(sharedPreferences.getString(USER_EMAIL,null));
                password.setText(sharedPreferences.getString(USER_PASSWORD,null));
                login.performClick();
            }
            else setVisibility(View.VISIBLE);

        }catch (Exception e){
            setVisibility(View.VISIBLE);
            System.out.println("Exception:無法取得登入成功紀錄");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.VISIBLE);
            }
        }, 2000);
        return view;
    }

    public void cleanAutoLogin(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL,"");
        editor.putString(USER_PASSWORD,"");
        editor.putString(LOGIN_SUCESS,"false");
        editor.apply();
    }

    public void setVisibility(int visibility){
        checkBox.setVisibility(visibility);
        divider.setVisibility(visibility);
        reg.setVisibility(visibility);
        serviceImage.setVisibility(visibility);
        languageImage.setVisibility(visibility);
        language.setVisibility(visibility);
        register.setVisibility(visibility);
        service.setVisibility(visibility);
        forgot_password.setVisibility(visibility);
        login.setVisibility(visibility);
        email.setVisibility(visibility);
        password.setVisibility(visibility);
    }



}
