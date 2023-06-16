package com.example.ntust_ap_server.lottery.Item;

// 2021.11.19 Boyan Start

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.example.ntust_ap_server.lottery.Request.LotteryRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "lottery")
public class LotteryItem {

        // token 自動序列化
        @SequenceGenerator(name = "lotteryitem_sequence", sequenceName = "lotteryitem_sequence", allocationSize = 1)
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lotteryitem_sequence")
        private Long Id;

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

        public LotteryItem(LotteryRequest request) {

                if (request.getId() != null) {
                        this.Id = request.getId();
                } else {
                        System.out.println("SurveyItem id is empty");
                }

                this.title = request.getTitle();
                this.description = request.getDescription();
                this.image_path = request.getImage_path();

                this.host = request.getHost();
                this.hostID = request.getHostID();
                this.host_email = request.getHost_email();
                this.host_phone_number = request.getHost_phone_number();

                this.probability = request.getProbability();
                this.left_amount = request.getLeft_amount();

                this.end_date = request.getEnd_date();
                this.status = request.getStatus();
        }

        @Override
        public String toString() {
                return "Survey Object{" +
                                ", survey_id=" + String.valueOf(this.Id) +
                                ", user_id=" + this.host +
                                '}';
        }

}
