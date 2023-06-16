package com.example.ntust_ap_server.survey.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.example.ntust_ap_server.survey.Request.*;

@Getter
@Setter
@Entity
@Table(name = "currentsurveyparticipents")
public class CurrentSurveyParticipentItem {

        @SequenceGenerator(name = "surveyparticipentitem_sequence", sequenceName = "surveyparticipentitem_sequence", allocationSize = 1)
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "surveyparticipentitem_sequence")
        private Long Id;

        private Long surveyId;
        private String userId;

        private String userName;//參與者名稱
        private int school;//參與者校別
        private int grade;// 參與者年級

        private String phone;//參與者電話

        public CurrentSurveyParticipentItem() {
        }

        public CurrentSurveyParticipentItem(
                Long surveyId, String userId,
                String userName,int school,int grade,
                String phone) {
                this.surveyId = surveyId;
                this.userId = userId;
                this.userName = userName;
                this.school = school;
                this.grade= grade;
                this.phone = phone;
        }


        public CurrentSurveyParticipentItem(ParticipentRequest request){
                this.surveyId = request.getSurveyId();
                this.userId = request.getUserId();
                this.userName = request.getUserName();
                this.school = request.getSchool();
                this.grade = request.getGrade();
                this.phone = request.getPhone();
        }
}
