package com.example.ntust_ap_server.activity.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "reason_cancel")
public class ReasonActivityCancelItem {
    
    @SequenceGenerator(name = "reason_cancel_sequence", sequenceName = "reason_cancel_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reason_cancel_sequence")
    private Long id;

    private Long activityId;// 活動ID
    private String requestUserId;// 要求者
    private String reason;//取消原因

    /**
     * 原因物件
     * @param activityId
     * @param requestUserId
     * @param reason
     */
    public ReasonActivityCancelItem(Long activityId, String requestUserId, String reason){
        this.activityId=activityId;
        this.requestUserId = requestUserId;
        this.reason = reason;
    }

}
