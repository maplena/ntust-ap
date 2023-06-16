package com.example.ntust_ap_server.survey.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParticipentRequest {
    private Long surveyId;
    private String userId;
    private String userName;
    private int school;// 學校 1=NTU 2=NTNU 3=NTUST
    private int grade;// 年級 1=大一 3=大三 5=碩士 6=博士
    private String phone;
    private int gender;//男=1, 女=2

}
