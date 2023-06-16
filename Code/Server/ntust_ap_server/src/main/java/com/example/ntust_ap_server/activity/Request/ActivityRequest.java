package com.example.ntust_ap_server.activity.Request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActivityRequest {
    //Requierd 基本參數
    private String title;// 活動名稱
    private String description;// 活動內容 簡介
    private String image_path;// 活動圖片路徑

    // 聯絡人部分
    private String host;// 主辦者名稱 可為個人暱稱 社團名稱 學校名稱 公司名稱
    private String hostID;// 主辦者ID
    private String host_email;// 主辦者 email
    private String host_phone_number;// 主辦者 連絡電話

    // 活動屬性
    private String school;// 校別 分別是 NTU NTNU NTUST
    private String place;// 地點
    private String tag;// 活動標籤
    private String type;// 主辦種類 自辦 社團 校辦

    // 活動參數設定
    private int school_limit = 0;// 學校限制 0=無限制 1=NTU 2=NTNU 3=NTUST
    private int gender;// 不限=0, 男=1, 女=2
    private int people_num_limit;// 人數上限
    private int price = 0;// 活動報名金額
    private int meal = 0;// 無提供=0 葷=1, 素=2, 都有供應=3
    private int grade = 0;// 年級限制 0=無限制 1=大一 3=大三 5=碩士 6=博士

    

    // 參加名單另外開資料庫 主鍵為活動ID

    // 活動時間
    // 分成 報名開放、截止 及 活動開始、結束
    // 立即活動 可以視作 開放時間同活動開始 截止時間同活動結束
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date open_date;// 開放報名日期

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date deadline_date;// 報名截止日期

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date start_date;// 活動開始日期

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date end_date;// 活動結束日期

    private String status;// 活動狀態 running報名開放中 lcok截止報名 end活動結束(歷史資料)


    //Optional
    //增加活動時必要參數 因活動舉辦者必參加該活動
    private int userSchool;
    private int userGrade;
    private int userGender;
    private int userMeal;

    // 修改活動時必要參數 須檢測是否為本人發送 以及須更新的活動ID
    private Long Id;
    private String requestUserId;
}
