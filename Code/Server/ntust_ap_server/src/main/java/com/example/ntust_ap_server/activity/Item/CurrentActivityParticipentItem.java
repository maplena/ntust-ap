package com.example.ntust_ap_server.activity.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.example.ntust_ap_server.activity.Request.ActivityRequest;
import com.example.ntust_ap_server.activity.Request.ParticipentRequest;

@Getter
@Setter
@Entity
@Table(name = "currentactivityparticipents")
public class CurrentActivityParticipentItem {

        @SequenceGenerator(name = "activityparticipentitem_sequence", sequenceName = "activityparticipentitem_sequence", allocationSize = 1)
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activityparticipentitem_sequence")
        private Long Id;

        private Long activityId;
        private String userId;

        private String userName;//參與者名稱
        private int school;//參與者校別
        private int gender;//參與者性別
        private int grade;// 參與者年級

        private String phone;//參與者電話
        private int meal;//葷素食

        public CurrentActivityParticipentItem() {
        }

        public CurrentActivityParticipentItem(
                Long activityId, String userId,
                String userName,int school,int grade,
                String phone,int meal) {
                this.activityId = activityId;
                this.userId = userId;
                this.userName = userName;
                this.school = school;
                this.grade= grade;
                this.phone = phone;
                this.meal = meal;
        }

        public CurrentActivityParticipentItem(Long activityId, ActivityRequest request) {
                //此參加適用於建立活動時
                this.activityId = activityId;
                this.userId = request.getHostID();
                this.userName = request.getHost();
                this.school = request.getUserSchool();
                this.gender = request.getGender();
                this.grade = request.getUserGrade();
                this.phone = request.getHost_phone_number();
                this.meal = request.getUserMeal();
        }

        public CurrentActivityParticipentItem(ParticipentRequest request){
                // 此參加適用於參與活動時
                this.activityId = request.getActivityId();
                this.userId = request.getUserId();
                this.userName = request.getUserName();
                this.school = request.getSchool();
                this.grade = request.getGrade();
                this.gender=request.getGender();
                this.phone = request.getPhone();
                this.meal = request.getMeal();
        }
}
