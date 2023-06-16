package com.example.ntust_ap_server.lottery.Request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LotteryRequest {
    
    // Requierd 基本參數
    private String title;// 問卷名稱
    private String description;// 問卷內容 簡介
    private String image_path;// 問卷圖片路徑

    // 聯絡人部分
    private String host;// 主辦者名稱 可為個人暱稱 社團名稱 學校名稱 公司名稱
    private String hostID;// 主辦者ID
    private String host_email;// 主辦者 email
    private String host_phone_number;// 主辦者 連絡電話

    private Float probability;
    private int left_amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date end_date;// 問卷結束日期

    private String status;// 問卷狀態 running報名開放中 lcok截止報名 end問卷結束(歷史資料)


    // Optional
    // 修改問卷時必要參數 須檢測是否為本人發送 以及須更新的問卷ID
    private Long Id;
    private String requestUserId;
}
