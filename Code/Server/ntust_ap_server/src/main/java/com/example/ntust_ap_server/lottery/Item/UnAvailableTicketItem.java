package com.example.ntust_ap_server.lottery.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "unavailableticket")
public class UnAvailableTicketItem {
    @SequenceGenerator(name = "ticket_sequence", sequenceName = "ticket_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_sequence")
    private Long Id;
    

    // Requierd 基本參數
    private Long LotteryId;
    private String title;// 問卷名稱
    private String description;// 問卷內容 簡介
    private String image_path;// 問卷圖片路徑
    private String hash_sequence;

    // 聯絡人部分
    private String host;// 主辦者名稱 可為個人暱稱 社團名稱 學校名稱 公司名稱
    

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date end_date;// 問卷結束日期

    public UnAvailableTicketItem(AvailableTicketItem ticket){
        this.Id = ticket.getId();
        this.LotteryId = ticket.getLotteryId();
        this.description = ticket.getDescription();
        this.image_path = ticket.getImage_path();
        this.hash_sequence = ticket.getHash_sequence();
        this.host = ticket.getHost();
        this.end_date = ticket.getEnd_date();
    }

}
