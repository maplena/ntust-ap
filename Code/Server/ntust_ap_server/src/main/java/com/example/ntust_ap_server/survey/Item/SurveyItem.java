package com.example.ntust_ap_server.survey.Item;

// 2021.11.19 Boyan Start

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.example.ntust_ap_server.survey.Request.SurveyRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "surveys")
public class SurveyItem {

        // token 自動序列化
        @SequenceGenerator(name = "confirmation_surveyitem_sequence", sequenceName = "confirmation_surveyitem_sequence", allocationSize = 1)
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_surveyitem_sequence")
        private Long Id;

        // Requierd 基本參數
        private String title;// 問卷名稱
        private String url;// 問卷網址
        private String description;// 問卷內容 簡介
        private String image_path;// 問卷圖片路徑

        // 聯絡人部分
        private String host;// 主辦者名稱 可為個人暱稱 社團名稱 學校名稱 公司名稱
        private String hostID;// 主辦者ID
        private String host_email;// 主辦者 email
        private String host_phone_number;// 主辦者 連絡電話

        // 問卷屬性
        private String tag;// 問卷標籤

        // 問卷參數設定
        private int school_limit = 0;// 學校限制 0=無限制 1=NTU 2=NTNU 3=NTUST
        private int gender;// 不限=0, 男=1, 女=2
        private int people_num_limit;// 人數上限
        private int grade = 0;// 年級限制 0=無限制 1=大一 3=大三 5=碩士 6=博士

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private Date start_date;// 問卷開始日期

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private Date end_date;// 問卷結束日期

        private String status;// 問卷狀態 running報名開放中 lcok截止報名 end問卷結束(歷史資料)

        // 初始化

        // Des:add attribute "type" 種類:校辦 自辦 等 //Date:2021.12.10//By:CLH

        public SurveyItem(SurveyRequest request) {

                if (request.getId() != null) {
                        this.Id = request.getId();
                } else {
                        System.out.println("SurveyItem id is empty");
                }

                this.title = request.getTitle();
                this.url = request.getUrl();
                this.description = request.getDescription();
                this.image_path = request.getImage_path();

                this.host = request.getHost();
                this.hostID = request.getHostID();
                this.host_email = request.getHost_email();
                this.host_phone_number = request.getHost_phone_number();

                this.tag = request.getTag();

                this.school_limit = request.getSchool_limit();
                this.gender = request.getGender();
                this.people_num_limit = request.getPeople_num_limit();
                this.grade = request.getGrade();

                this.start_date = request.getStart_date();
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
