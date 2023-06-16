package com.example.ntust_ap_server.survey.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "pastsurveyparticipents")
public class PastSurveyParticipentItem {
    @SequenceGenerator(name = "surveyparticipentitem_sequence", sequenceName = "surveyparticipentitem_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "surveyparticipentitem_sequence")
    private Long Id;

    private Long surveyId;
    private String userId;

    private String userName;// 參與者名稱
    private int school;// 參與者校別
    private int grade;// 參與者年級

    private String phone;// 參與者電話

    public PastSurveyParticipentItem() {
    }

    public PastSurveyParticipentItem(CurrentSurveyParticipentItem currentActivityParticipentItem) {
        this.surveyId = currentActivityParticipentItem.getSurveyId();
        this.userId = currentActivityParticipentItem.getUserId();
        this.userName = currentActivityParticipentItem.getUserName();
        this.school= currentActivityParticipentItem.getSchool();
        this.grade=currentActivityParticipentItem.getGrade();
        this.phone=currentActivityParticipentItem.getPhone();
    }
}
