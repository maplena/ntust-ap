package com.example.ntust_ap_server.activity.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "pastactivityparticipents")
public class PastActivityParticipentItem {
    @SequenceGenerator(name = "activityparticipentitem_sequence", sequenceName = "activityparticipentitem_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activityparticipentitem_sequence")
    private Long Id;

    private Long activityId;
    private String userId;

    private String userName;// 參與者名稱
    private int school;// 參與者校別
    private int gender;// 參與者性別
    private int grade;// 參與者年級

    private String phone;// 參與者電話
    private int meal;// 葷素食
    public PastActivityParticipentItem() {
        }
    
    public PastActivityParticipentItem(CurrentActivityParticipentItem currentActivityParticipentItem) {
        this.activityId = currentActivityParticipentItem.getActivityId();
        this.userId = currentActivityParticipentItem.getUserId();
        this.userName = currentActivityParticipentItem.getUserName();
        this.school = currentActivityParticipentItem.getSchool();
        this.gender = currentActivityParticipentItem.getGender();
        this.grade = currentActivityParticipentItem.getGrade();
        this.phone = currentActivityParticipentItem.getPhone();
        this.meal = currentActivityParticipentItem.getMeal();
        }
}
