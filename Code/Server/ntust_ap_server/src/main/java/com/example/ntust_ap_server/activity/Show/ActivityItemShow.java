package com.example.ntust_ap_server.activity.Show;

// 2021.11.19 Boyan Start

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import com.example.ntust_ap_server.activity.Item.ActivityItem;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ActivityItemShow {

        private Long Id;

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

        private int now_people;
        // 初始化
        public ActivityItemShow(
                        Long id,
                        String title, String description, String image_path,
                        String host, String hostID, String host_email, String host_phone_number,
                        String school, String place, String tag, String type,
                        int gender, int people_num_limit, int price, int meal, int grade,
                        Date open_date, Date deadline_date, Date start_date, Date end_date,
                        String status) {
                this.Id = id;
                //
                this.title = title;
                this.description = description;
                this.image_path = image_path;
                //
                this.host = host;
                this.hostID = hostID;
                this.host_email = host_email;
                this.host_phone_number = host_phone_number;
                //
                this.school = school;
                this.place = place;
                this.tag = tag;
                this.type = type;
                //
                this.gender = gender;
                this.people_num_limit = people_num_limit;
                this.price = price;
                this.meal = meal;
                this.grade = grade;
                //
                this.open_date = open_date;
                this.deadline_date = deadline_date;
                this.start_date = start_date;
                this.end_date = end_date;
                //
                this.status = status;
        }

        public ActivityItemShow(ActivityItem item,int now_people){
                this.Id = item.getId();
                this.title=item.getTitle();
                this.description=item.getDescription();
                this.image_path=item.getImage_path();
                this.host=item.getHost();
                this.hostID=item.getHostID();
                this.host_email=item.getHost_email();
                this.host_phone_number=item.getHost_phone_number();
                this.school=item.getSchool();
                this.place=item.getPlace();
                this.tag=item.getTag();
                this.type=item.getType();
                this.school_limit=item.getSchool_limit();
                this.gender=item.getGender();
                this.people_num_limit=item.getPeople_num_limit();
                this.price=item.getPrice();
                this.meal=item.getMeal();
                this.grade=item.getGrade();
                this.open_date=item.getOpen_date();
                this.deadline_date=item.getDeadline_date();
                this.start_date=item.getStart_date();
                this.end_date=item.getEnd_date();
                this.status=item.getStatus();
                this.now_people=now_people;
        }


       
}
