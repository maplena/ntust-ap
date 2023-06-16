package com.example.figma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.figma.api.RetrofitClient;
import com.example.figma.api.UpdatePassword;
import com.example.figma.api.User;

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
public class f3_3_1 extends Fragment {
    Button finish_change_password;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText password, updatepassword1, updatpassword2;

    public f3_3_1() {
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
    public static f3_3_1 newInstance(String param1, String param2) {
        f3_3_1 fragment = new f3_3_1();
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
        View view = inflater.inflate(R.layout.f3_3_1, container, false);

        password = view.findViewById(R.id.OtherPersonalInterestEditText);
        updatepassword1 = view.findViewById(R.id.editText9);
        updatpassword2 = view.findViewById(R.id.editText10);


        // Inflate the layout for this fragment
        finish_change_password = view.findViewById(R.id.FinishChangePasswordButton);
        finish_change_password.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (updatepassword1.getText().toString().trim().equals(updatpassword2.getText().toString().trim())){
                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .updatePassword(new UpdatePassword(
                                    Global.email,
                                    password.getText().toString().trim(),
                                    updatepassword1.getText().toString().trim()));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            String s = "";
                            try {
                                if (response.body()!=null){s = response.body().string();}
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                            if (s!=""){
                                Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
                                Global.password = updatepassword1.getText().toString().trim();
                            }else {
                                Toast.makeText(getActivity().getApplicationContext(), "合法性驗證失敗", Toast.LENGTH_LONG).show();
                            }
                            Navigation.findNavController(view).navigate(R.id.action_f3_3_1_to_f3_3);
//                            System.out.println(Global.email);
//                            System.out.println(password.getText().toString().trim());
//                            System.out.println(updatepassword1.getText().toString().trim());
//                            System.out.println(s);

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "再次確認新密碼錯誤", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}