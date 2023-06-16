package com.example.ntust_ap_server.survey.Request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString


public class GetSurveyRequest {
    private int school_limit = 0;// 學校限制 0=無限制 1=NTU 2=NTNU 3=NTUST
    private final int gender;// 不限=0, 男=1, 女=2
    private int grade = 0;// 年級限制 0=無限制 1=大一 3=大三 5=碩士 6=博士
    private int page;
}
