package com.example.ntust_ap_server.lottery.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.example.ntust_ap_server.lottery.Request.LotteryParticipentRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "availableticket")
public class AvailableTicketItem {
    @SequenceGenerator(name = "ticket_sequence", sequenceName = "ticket_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_sequence")
    private Long Id;
    

    // Requierd 基本參數
    private Long LotteryId;//獎項
    private String userId;
    private String title;// 獎卷名稱
    private String description;// 獎卷內容 簡介
    private String image_path;// 獎卷圖片路徑
    private String hash_sequence;//獎券隨機hash

    // 聯絡人部分
    private String host;// 主辦者名稱 可為個人暱稱 社團名稱 學校名稱 公司名稱
    

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date end_date;// 獎卷結束日期

    public AvailableTicketItem(LotteryParticipentRequest request){
        this.LotteryId = request.getLotteryId();
        this.userId = request.getUserId();
    }
    
}
