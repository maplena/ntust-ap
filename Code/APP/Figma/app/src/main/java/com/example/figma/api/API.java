package com.example.figma.api;

import com.example.figma.DelFriendItem;
import com.example.figma.Eventtest.EventItem;
import com.example.figma.Eventtest.ParticipentItem;
import com.example.figma.Eventtest.RatingItem;
import com.example.figma.Lottery;
import com.example.figma.Question;
import com.example.figma.SendFriendApplyItem;
import com.example.figma.Ticket;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface API {
    @POST("/api/v1/edit_user/get_user_private")
        //取得私人資料
    Call<ResponseBody> checkUser(
            @Body User user
    );

    @POST("/api/v1/registration")
        //註冊
    Call<ResponseBody> registration(
            @Body RegistrationUser registrationUser
    );

    @POST("/api/v1/resetPassword")
        //重設密碼
    Call<ResponseBody> resetPassword(
            @Body User user
    );

    @POST("/api/v1/resetPassword/update")
        //更新密碼
    Call<ResponseBody> updatePassword(
            @Body UpdatePassword updatePassword
    );

    @POST("api/v1/image/user/download")
        //下載照片
    Call<ResponseBody> image_user_download(
            @Body RequestBody body
    );

    @POST("api/v1/friend/get_friendlist")
        //讀取好友列
    Call<ResponseBody> get_friendlist(
            @Body User user
    );

    @POST("api/v1/edit_user/get_user_public")
        //讀取公開資料
    Call<ResponseBody> get_user_public(
            @Body RequestBody body
    );

    @POST("api/v1/edit_user/get_user_public_byname")
        //讀取公開資料 (用studentid or name)
    Call<ResponseBody> get_user_public_byname(
            @Body RequestBody body
    );

    @POST("api/v1/edit_user/get_user_public_bystudentid")
        //讀取公開資料 (用studentid)
    Call<ResponseBody> get_user_public_bystudentid(
            @Body RequestBody body
    );

    @POST("api/v1/friend/get_friend_apply")
        //讀取好友申請
    Call<ResponseBody> get_friend_apply(
            @Body User user
    );

    @POST("api/v1/edit_user/update_user_data")
        //更新個人資料
    Call<ResponseBody> update_user_data(
            @Body UpdateUserData updateUserData
    );

    @POST("api/v1/friend/del_friend")
        //更新個人資料
    Call<ResponseBody> del_friend(
            @Body DelFriendItem delFriendItem
    );

    @POST("api/v1/friend/send_friend_apply")
        //發送好友申請
    Call<ResponseBody> send_friend_apply(
            @Body SendFriendApplyItem sendFriendApplyItem
    );

    @POST("api/v1/friend/del_friend_apply")
        //拒絕好友申請
    Call<ResponseBody> del_friend_apply(
            @Body SendFriendApplyItem sendFriendApplyItem
    );

    @Multipart
    @POST("api/v1/image/user/upload")
    Call<ResponseBody> update_image(
            @Part MultipartBody.Part files
    );

    // --- --- --- --- --- ---
    @GET("/api/v1/activity/get/type=自辦&status=running/{page}")
//獲得以type排序的活動列表
    Call<ResponseBody> getActivityByType(
            @Path("page") Integer page
    );

    @Multipart
    @POST("/api/v1/activity/add")
    Call<ResponseBody> addActivity(
            @Part("request") RequestBody request,
            @Part MultipartBody.Part img
    );

    //取得活動
    @GET("/api/v1/activity/get/{id}")
//獲得以type排序的活動列表
    Call<ResponseBody> getActivityById(
            @Path("id") Long Id
    );

    //更新活動
    @Multipart
    @POST("/api/v1/activity/update")
    Call<ResponseBody> UpdateActivity(
            @Part("request") RequestBody request,
            @Part MultipartBody.Part img
    );

    //下架活動
    @POST("/api/v1/activity/stop")
    Call<ResponseBody> CancelActivity(
            @Body RequestBody body
    );

    //參加活動
    @POST("/api/v1/activity/joinactivity")
    Call<ResponseBody> JoinActivity(
             @Body RequestBody body
    );

    //離開活動
    @POST("/api/v1/activity/leaveactivity")
    Call<ResponseBody> LeaveActivity(
            @Body RequestBody body
    );

    //踢出活動
    @POST("/api/v1/activity/kickactivity")
    Call<ResponseBody> KickActivityMember(
            @Body RequestBody body
    );

    //取得對應id活動(當前的)參與者列表
    @GET("/api/v1/activity/getparticipent/current/{id}/{page}")
    //獲得對應id活動的人員列表
    Call<List<ParticipentItem>> getActivityMember(
            @Path("id") Integer aid, @Path("page") Integer page
    );

    //取得我當前參加的活動列表
    @GET("/api/v1/activity/getmyactivities/current/{id}/{page}")
    Call<List<EventItem>> getJoinActivity(
            @Path("id") String sid, @Path("page") Integer page
    );
    //取得活動照片

    /*活動 取得活動列表--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ------ --- --- --- */

    @POST("/api/v1/activity/getbylimit")
//獲得以type排序的活動列表
    Call<List<EventItem>> getActivityByLimit(
            @Body RequestBody body
    );


    @GET("/api/v1/activity/get/type={type}&status=running/{page}")
//獲得以type排序的活動列表
    Call<List<EventItem>> getActivityByType(
            @Path("type") String type,
            @Path("page") Integer page

    );

    @GET("/api/v1/activity/get/host={sid}&status={status}/{page}")
//獲得以發起人排序的活動列表
    Call<List<EventItem>> getActivityByPromoter(
            @Path("sid") String sid, @Path("status") String status, @Path("page") Integer page
    );
    /*活動 歷史活動--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ------ --- --- --- */

    //獲得對應學號參加的歷史活動的列表
    @GET("/api/v1/activity/getmyactivities/past/{id}/{page}")
    Call<List<EventItem>> getPastActivity(
            @Path("id") String sid, @Path("page") Integer page
    );

    //取得對應id活動(過去的)參與者列表
    @GET("/api/v1/activity/getparticipent/past/{id}/{page}")

    //獲得對應id活動的人員列表
    Call<ResponseBody> getPastActivityMember(
            @Path("id") Integer aid, @Path("page") Integer page
    );
    /*活動 活動評價--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ------ --- --- --- */

    //取得活動的評價列表
    @GET("/api/v1/activity/getratinglist/{activityid}/{page}")
    Call<List<RatingItem>> getActivityRateList(
            @Path("activityid") String aid,@Path("page") Integer page
    );
    //取得活動的平均分數
    @GET("/api/v1/activity/getrating/{activityid}")
    Call<ResponseBody> getActivityRate(
            @Path("activityid") String aid
    );

    @POST("/api/v1/activity/rating")
    Call<ResponseBody> RateActivity(
            @Body RequestBody body
    );

    /*當前問卷 --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ------ --- --- --- */

    @Multipart
    @POST("/api/v1/survey/add")
    Call<ResponseBody> addSurvey(
            @Part("request") RequestBody request,
            @Part MultipartBody.Part img
    );

    //取得問卷
    @GET("/api/v1/survey/get/{id}")
//獲得以type排序的問卷列表
    Call<Question> getSurveyById(
            @Path("id") Long Id
    );

    //更新問卷
    @Multipart
    @POST("/api/v1/survey/update")
    Call<ResponseBody> UpdateSurvey(
            @Part("request") RequestBody request,
            @Part MultipartBody.Part img
    );

    //下架問卷
    @POST("/api/v1/survey/stop")
    Call<ResponseBody> CancelSurvey(
            @Body RequestBody body
    );

    //參加問卷
    @POST("/api/v1/survey/joinsurvey")
    Call<ResponseBody> JoinSurvey(
            @Body RequestBody body
    );


    //取得對應id問卷(當前的)參與者列表
    @GET("/api/v1/survey/getparticipent/current/{id}/{page}")
    //獲得對應id問卷的人員列表
    Call<List<ParticipentItem>> getSurveyMember(
            @Path("id") Integer aid, @Path("page") Integer page
    );

    //取得我當前參加的問卷列表
    @GET("/api/v1/activity/getmysurveys/current/{id}/{page}")
    Call<List<Question>> getJoinSurvey(
            @Path("id") String sid, @Path("page") Integer page
    );

    /*問卷 取得問卷列表--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ------ --- --- --- */

    @POST("/api/v1/survey/getbylimit")
//獲得以type排序的問卷列表
    Call<List<Question>> getSurveyByLimit(
            @Body RequestBody body
    );


    @GET("/api/v1/survey/get/type={type}&status=running/{page}")
//獲得以type排序的問卷列表
    Call<List<Question>> getSurveyByType(
            @Path("type") String type,
            @Path("page") Integer page

    );

    @GET("/api/v1/survey/get/host={sid}&status={status}/{page}")
//獲得以發起人排序的問卷列表
    Call<List<Question>> getSurveyByHost(
            @Path("sid") String sid, @Path("status") String status, @Path("page") Integer page
    );
    /*問卷 歷史問卷--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ------ --- --- --- */

    //獲得對應學號參加的歷史問卷的列表
    @GET("/api/v1/survey/getmysurveys/past/{id}/{page}")
    Call<List<Question>> getPastSurvey(
            @Path("id") String sid, @Path("page") Integer page
    );

    //取得對應id問卷(過去的)參與者列表
    @GET("/api/v1/survey/getparticipent/past/{id}/{page}")
    //獲得對應id問卷的人員列表
    Call<ResponseBody> getPastSurveyMember(
            @Path("id") Integer aid, @Path("page") Integer page
    );
    /*抽獎 取得抽獎列表--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ------ --- --- --- */

    //獲得抽獎列表
    @POST("/api/v1/lottery/getbylimit")
    Call<List<Lottery>> getLottery(
            @Body RequestBody body
    );

    //抽獎
    @POST("/api/v1/lottery/draw")
    Call<ResponseBody> LotteryDraw(
            @Body RequestBody body
    );

    //獲得個人可用優惠卷列表
    @GET("/api/v1/lottery/getmylottery/current/{sid}/{page}")
    Call<List<Ticket>> getMyLottery(
            @Path("sid")String sid,@Path("page") Integer page
    );

    //使用優惠卷
    @GET("/api/v1/lottery/use/{hash}")
    Call<RequestBody> useMyLottery(
            @Path("hash")String hash
    );
}