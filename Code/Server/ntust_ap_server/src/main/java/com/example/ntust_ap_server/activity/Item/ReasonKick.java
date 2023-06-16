package com.example.ntust_ap_server.activity.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "reason_kick")
public class ReasonKick {
    
    @SequenceGenerator(name = "reason_kick_sequence", sequenceName = "reason_kick_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reason_kick_sequence")
    private Long id;

    private Long activityId;//活動ID
    private String requestUserId;//要求者
    private String userId;//操作對象
    private String reason;//剔除原因

    /**
     * 剔除原因 物件 
     * @param activityId    活動ID
     * @param requestUserId 要求者ID
     * @param userId        對象ID
     * @param reason        原因
     */
    public ReasonKick(Long activityId,String requestUserId, String userId, String reason){
        this.activityId = activityId;
        this.requestUserId=requestUserId;
        this.userId=userId;
        this.reason=reason;
    }

}
