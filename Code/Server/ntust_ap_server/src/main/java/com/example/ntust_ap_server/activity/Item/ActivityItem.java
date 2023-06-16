package com.example.ntust_ap_server.activity.Item;

// 2021.11.19 Boyan Start

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.example.ntust_ap_server.activity.Request.ActivityRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "activities")
public class ActivityItem {

        /*
         * 自動產生ID
         * 活動資料庫有兩種 當前 與 歷史資料庫
         * 但活動ID是獨立的 不會因為在不同資料庫而改動
         * 所以共用ID產生器 產生的ID不會重複 不會衝突
         */
        @SequenceGenerator(name = "confirmation_activityitem_sequence", sequenceName = "confirmation_activityitem_sequence", allocationSize = 1)
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_activityitem_sequence")
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

        // 初始化
        public ActivityItem(
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

        public ActivityItem(ActivityRequest request){
                // if(request.getId()!=null){
                // this.Id=request.getId();
                // }
                // else{System.out.println("ActivityItem id is empty");}
                this.Id = request.getId();
                this.title=request.getTitle();
                this.description=request.getDescription();
                this.image_path=request.getImage_path();
                this.host=request.getHost();
                this.hostID=request.getHostID();
                this.host_email=request.getHost_email();
                this.host_phone_number=request.getHost_phone_number();
                this.school=request.getSchool();
                this.place=request.getPlace();
                this.tag=request.getTag();
                this.type=request.getType();
                this.school_limit=request.getSchool_limit();
                this.gender=request.getGender();
                this.people_num_limit=request.getPeople_num_limit();
                this.price=request.getPrice();
                this.meal=request.getMeal();
                this.grade=request.getGrade();
                this.open_date=request.getOpen_date();
                this.deadline_date=request.getDeadline_date();
                this.start_date=request.getStart_date();
                this.end_date=request.getEnd_date();
                this.status=request.getStatus();
        }


       
}
