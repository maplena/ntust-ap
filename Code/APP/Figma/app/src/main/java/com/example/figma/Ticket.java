package com.example.figma;

public class Ticket {

    private String userId;
    private String title;
    private String description;
    private Object imagePath;
    private String hash_sequence;
    private String host;
    private String end_date;
    private Integer id;
    private Integer lotteryId;

    public Ticket(String userId, String title, String description, Object imagePath, String hashSequence, String host, String endDate, Integer id, Integer lotteryId) {
        super();
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.hash_sequence = hashSequence;
        this.host = host;
        this.end_date = endDate;
        this.id = id;
        this.lotteryId = lotteryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getImagePath() {
        return imagePath;
    }

    public void setImagePath(Object imagePath) {
        this.imagePath = imagePath;
    }

    public String getHashSequence() {
        return hash_sequence;
    }

    public void setHashSequence(String hashSequence) {
        this.hash_sequence = hashSequence;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String endDate) {
        this.end_date = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

}