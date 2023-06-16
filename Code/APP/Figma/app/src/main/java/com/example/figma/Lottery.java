package com.example.figma;


public class Lottery {

    private String title;
    private String description;
    private String imagePath;
    private String host;
    private String hostID;
    private String hostEmail;
    private String hostPhoneNumber;
    private Double probability;
    private Integer left_amount;
    private String end_date;
    private String status;
    private Integer id;

    public Lottery(String title,String description,String imagePath,String host,String hostID,String hostEmail,String hostPhoneNumber,Double probability,Integer leftAmount,String endDate,String status,Integer id){
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.host = host;
        this.hostID = hostID;
        this.hostEmail = hostEmail;
        this.hostPhoneNumber = hostPhoneNumber;
        this.probability = probability;
        this.left_amount = leftAmount;
        this.end_date = endDate;
        this.status = status;
        this.id = id;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getHostPhoneNumber() {
        return hostPhoneNumber;
    }

    public void setHostPhoneNumber(String hostPhoneNumber) {
        this.hostPhoneNumber = hostPhoneNumber;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Integer getLeftAmount() {
        return left_amount;
    }

    public void setLeftAmount(Integer leftAmount) {
        this.left_amount = leftAmount;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String endDate) {
        this.end_date = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}