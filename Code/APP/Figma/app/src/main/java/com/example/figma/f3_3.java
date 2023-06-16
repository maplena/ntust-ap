package com.example.figma;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.figma.api.RetrofitClient;
import com.example.figma.api.UpdateUserData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
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
public class f3_3 extends Fragment {
    Button change_password;
    Button finish_change;
    Button man_btn, female_btn, single_btn, unsingle_btn, noturbis_btn;
    ImageButton return_btn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView personalId, personalName, introduce, interest;
    private TextView tag;

    private ImageView imageView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int IMG_REQUEST = 21;

    //更新用參數
    private String update_personalName, update_selfinfo, update_introduce, update_gender, update_relationship;


    public f3_3() {
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
    public static f3_3 newInstance(String param1, String param2) {
        f3_3 fragment = new f3_3();
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
        View view = inflater.inflate(R.layout.f3_3, container, false);
        personalId = view.findViewById(R.id.PersonalStudentId);
        personalName = view.findViewById(R.id.PersonalName);
        tag = view.findViewById(R.id.f4211tag1);
        introduce = view.findViewById(R.id.OtherPersonalIntroduceEditText);
        interest = view.findViewById(R.id.OtherPersonalInterestEditText);

        imageView = view.findViewById(R.id.OtherPersonalImageView);
        imageView.setOnClickListener(view16 -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMG_REQUEST);

//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                Global.bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);

//                byte[] imageInByte = byteArrayOutputStream.toByteArray();
//
//                String encodedImage = Base64.getEncoder().encodeToString(imageInByte);
//                String filename = Global.studentid+".jpg";
//                File file = new File(encodedImage);

//                RequestBody requestFile =
//                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                MultipartBody.Part body =
//                        MultipartBody.Part.createFormData("file", filename, requestFile);

//                Call<ResponseBody> call = RetrofitClient.getInstance().getAPI().update_image(body);
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        System.out.println("Sucess");
//                    }
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    }
//                });
        });


        //性別相關按鈕控制
        man_btn = view.findViewById(R.id.FriendList);
        female_btn = view.findViewById(R.id.FriendApplyList);
        man_btn = view.findViewById(R.id.FriendList);
        female_btn = view.findViewById(R.id.FriendApplyList);
        man_btn.setOnClickListener(view15 -> {
            Global.gender = true;
            man_btn.setBackgroundResource(R.drawable.button_style1);
            female_btn.setBackgroundResource(R.drawable.button_style2);
        });
        female_btn.setOnClickListener(view14 -> {
            Global.gender = false;
            man_btn.setBackgroundResource(R.drawable.button_style2);
            female_btn.setBackgroundResource(R.drawable.button_style1);
        });
        if (Global.gender) {
            man_btn.setBackgroundResource(R.drawable.button_style1);
        } else {
            female_btn.setBackgroundResource(R.drawable.button_style1);
        }
        //感情相關按鈕控制
        single_btn = view.findViewById(R.id.RelationSingle);
        unsingle_btn = view.findViewById(R.id.RelationRomantic);
        noturbis_btn = view.findViewById(R.id.RelationOther);
        single_btn.setOnClickListener(view13 -> {
            single_btn.setBackgroundResource(R.drawable.button_style1);
            unsingle_btn.setBackgroundResource(R.drawable.button_style6);
            noturbis_btn.setBackgroundResource(R.drawable.button_style6);
            Global.relationship = 1;
        });
        unsingle_btn.setOnClickListener(view12 -> {
            single_btn.setBackgroundResource(R.drawable.button_style6);
            unsingle_btn.setBackgroundResource(R.drawable.button_style1);
            noturbis_btn.setBackgroundResource(R.drawable.button_style6);
            Global.relationship = 2;
        });
        noturbis_btn.setOnClickListener(view1 -> {
            single_btn.setBackgroundResource(R.drawable.button_style6);
            unsingle_btn.setBackgroundResource(R.drawable.button_style6);
            noturbis_btn.setBackgroundResource(R.drawable.button_style1);
            Global.relationship = 3;
        });
        if (Global.relationship == 1) {
            single_btn.setBackgroundResource(R.drawable.button_style1);
        } else if (Global.relationship == 2) {
            unsingle_btn.setBackgroundResource(R.drawable.button_style1);
        } else {
            noturbis_btn.setBackgroundResource(R.drawable.button_style1);
        }
        //反回上一步
        return_btn = view.findViewById(R.id.ReturnBtn);
        return_btn.setOnClickListener(v -> getActivity().onBackPressed());

        personalId.setText(Global.studentid);
        personalName.setText(Global.name);
//        tag.setText(Global.tag);
        tag.setText(Global.convertToTag());
        introduce.setText(Global.self_info);
        interest.setText(Global.interest);
        try {
            File imgFile = new File("/data/user/0/com.example.figma/files" + "/" + Global.image_path);

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

            imageView.setImageBitmap(backgroundBmp);

        } catch (Exception e) {
            System.out.println("System : imgFile.notexists");
        }

        // Inflate the layout for this fragment
        change_password = view.findViewById(R.id.ChangePasswordButton);
        change_password.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_f3_3_to_f3_3_1));

        finish_change = view.findViewById(R.id.FinishChangeButton);
        finish_change.setOnClickListener(v -> {
            int relationship = Global.relationship;
            Global.name = personalName.getText().toString();
            Global.self_info = introduce.getText().toString();
            System.out.println("Global.self_info:" + Global.self_info);
            Global.interest = interest.getText().toString();
            Global.image_path = Global.studentid + ".jpg";
            Global.tag = Global.convertToTag();
            Call<ResponseBody> call_update_user_data = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .update_user_data(new UpdateUserData(
                            Global.email,
                            Global.name,
                            Global.password,
                            String.valueOf(Global.relationship),
                            Global.gender,
                            Global.studentid + ".jpg",
                            Global.self_info,
                            Global.interest,
                            Global.convertToTag()));

            call_update_user_data.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String s = "";
                    try {
                        if (response.body() != null) s = response.body().string();
                    } catch (IOException e) {
                        System.out.println("Get response.body Fail");
                    }
                    System.out.println("Sucess : " + s);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    System.out.println("Fail");
                }
            });

            //create a file to write bitmap data
            try {
                File f = new File(getContext().getCacheDir(), Global.studentid + ".jpg");
                f.createNewFile();

                //Convert bitmap to byte array
                Bitmap bitmap = Global.bitmap;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                byte[] bitmapdata = bos.toByteArray();

                //write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();

//                    if (f.exists())System.out.println("f.exists() + "+f.getName() + "/" + f.getAbsolutePath());

                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), f);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("files", f.getName(), requestFile);

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .update_image(body);
                System.out.println(call);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        System.out.println("Upload success");
                        String s = "";
                        try {
                            if (response.body() != null) s = response.body().string();
                        } catch (IOException e) {
//                            e.printStackTrace();
                            System.out.println("Cant get respode body");
                        }
                        Global.bitmap = null; // 清理緩存
                        Navigation.findNavController(v).navigate(R.id.action_f3_3_to_f3_1);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("Upload error");
                    }
                });
            } catch (Exception e) {
                System.out.println("照片存入失敗");
                Global.bitmap = null;
                Navigation.findNavController(v).navigate(R.id.action_f3_3_to_f3_1);
            }

        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            Bitmap myBitmap = Global.bitmap;
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

            imageView.setImageBitmap(backgroundBmp);

        } catch (Exception e) {
            Log.d("F33","imgFile not exists");
        }
    }
}