package com.example.ntust_ap_server.survey.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ControlSurveyRequest {
    
    /**必要參數 */

    /** 活動ID */
    private Long surveyId;
    /** 傳送要求者ID */
    private String requestUserId;
    
    /** Option參數 */

    /** 操作對象 */
    private String userId;
    /** 原因 當下架、剔除時，可用於下架原因 or 剔除人員原因*/
    private String reason;// 

    
}
