package com.example.ntust_ap_server.lottery.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LotteryParticipentRequest {
    private Long lotteryId;
    private String userId;
}
