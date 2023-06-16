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

import com.example.figma.api.RetrofitClient;
import com.example.figma.api.User;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f1_1_1 extends Fragment {
    Button send_verification_code;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText email;
    private TextView textView;
    public f1_1_1() {
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
    public static f1_1_1 newInstance(String param1, String param2) {
        f1_1_1 fragment = new f1_1_1();
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
        View view = inflater.inflate(R.layout.f1_1_1, container, false);
        send_verification_code = view.findViewById(R.id.SendVerificationCodeButton);
        email = view.findViewById(R.id.editText2);
        textView = view.findViewById(R.id.textView3);
        send_verification_code.setOnClickListener(v -> {
//                RequestBody requestBody = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("email", email.getText().toString().trim())
//                        .build();

            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .resetPassword(new User(email.getText().toString().trim(),""));

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String s = "";
                    try {
                        if (response.body() != null) s = response.body().string();
                    } catch (IOException e) {
                    }
                    String token[] = s.split("-");
                    if (token.length == 5) {
                        Navigation.findNavController(v).navigate(R.id.f1_1);
                    } else {
                        System.out.println("Exception : Jason return problem");
                        System.out.println("token : "+ Arrays.toString(token));
                        textView.setText("該電子郵件尚未註冊");
                    }
                    System.out.println(s);
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("Fail : " + t);
                }
            });

//                Navigation.findNavController(v).navigate(R.id.f1_1);
        });
        // Inflate the layout for this fragment
        return view;
    }
}
