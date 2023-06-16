package com.example.figma;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.api.RetrofitClient;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendApplyRecycleAdapter extends RecyclerView.Adapter<FriendApplyRecycleAdapter.TaskViewHolder>{

    private Context mContext;
    public Friend friend;
    private List<Friend> friend_list;

    public FriendApplyRecycleAdapter(Context context, List<Friend> list) {
        mContext = context;
        friend_list = list;
    }

    @NonNull
    @Override
    public FriendApplyRecycleAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.f3_1_1_friend_item, parent, false);
        return new FriendApplyRecycleAdapter.TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendApplyRecycleAdapter.TaskViewHolder holder, int position) {
        friend = friend_list.get(position);
        //Set values
        try {
            File imgFile = new File("/data/user/0/com.example.figma/files"+"/"+friend.getImage_path());
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

            holder.friend_list_image.setImageBitmap(backgroundBmp);

        }catch (Exception e){
            System.out.println("System : imgFile.notexists.");
        }


        holder.friend_list_name.setText(friend.getName());
        holder.friend_list_student_id.setText(friend.getStudent_id());
        holder.friend_list_tag.setText(Global.temp_convertToTag(friend.getSchool(), friend.getGender(), friend.getStudent_id()));
    }

    @Override
    public int getItemCount() {
        if (friend_list == null) {
            return 0;
        }
        return friend_list.size();
    }

    public List<Friend> getTasks() {
        return friend_list;
    }

    public void setTasks(List<Friend> taskEntries) {
        friend_list = taskEntries;
        notifyDataSetChanged();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{
        ImageView friend_list_image;
        TextView friend_list_name, friend_list_student_id, friend_list_tag;
        Button delete_friend_btn;
        ConstraintLayout friend_Item_Layout;
        ImageView send_apply_btn;
        public TaskViewHolder(View itemView) {
            super(itemView);
            friend_list_image = itemView.findViewById(R.id.imageView19);
            friend_list_name = itemView.findViewById(R.id.textView0);
            friend_list_student_id = itemView.findViewById(R.id.PersonalStudentId);
            friend_list_tag = itemView.findViewById(R.id.f4211tag1);
            friend_Item_Layout = itemView.findViewById(R.id.FriendItem);
            friend_Item_Layout.setOnClickListener(view -> {
                Global.tempF7_studentid = friend_list_student_id.getText().toString();
                Navigation.findNavController(view).navigate(R.id.action_f3_1_1_to_f7);
            });

            send_apply_btn = itemView.findViewById(R.id.imageView18);
            send_apply_btn.setOnClickListener(view -> {

                Call<ResponseBody> call_del_friend = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .send_friend_apply(new SendFriendApplyItem(Global.email, Global.password, friend.getStudent_id()));
                call_del_friend.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String s = "";
                        try {
                            if (response.body()!=null)s = response.body().string();
                            Toast.makeText(mContext.getApplicationContext(), s, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            System.out.println("Get response.body Fail");
                        }
                        System.out.println(Global.email+ "/"+Global.password + "/"+ friend.getStudent_id()+"(qaaaaaq");
                        System.out.println(s);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    }
                });
            });
        }
    }
}
