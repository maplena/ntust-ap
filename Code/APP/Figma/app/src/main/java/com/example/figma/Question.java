package com.example.figma;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Question {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_path")
    @Expose
    private String imagePath;
    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("hostID")
    @Expose
    private String hostID;
    @SerializedName("host_email")
    @Expose
    private String hostEmail;
    @SerializedName("host_phone_number")
    @Expose
    private String hostPhoneNumber;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("school_limit")
    @Expose
    private Integer schoolLimit;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("people_num_limit")
    @Expose
    private Integer peopleNumLimit;
    @SerializedName("grade")
    @Expose
    private Integer grade;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Question withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Question withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Question withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Question withImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Question withHost(String host) {
        this.host = host;
        return this;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public Question withHostID(String hostID) {
        this.hostID = hostID;
        return this;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public Question withHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
        return this;
    }

    public String getHostPhoneNumber() {
        return hostPhoneNumber;
    }

    public void setHostPhoneNumber(String hostPhoneNumber) {
        this.hostPhoneNumber = hostPhoneNumber;
    }

    public Question withHostPhoneNumber(String hostPhoneNumber) {
        this.hostPhoneNumber = hostPhoneNumber;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Question withTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Integer getSchoolLimit() {
        return schoolLimit;
    }

    public void setSchoolLimit(Integer schoolLimit) {
        this.schoolLimit = schoolLimit;
    }

    public Question withSchoolLimit(Integer schoolLimit) {
        this.schoolLimit = schoolLimit;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Question withGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public Integer getPeopleNumLimit() {
        return peopleNumLimit;
    }

    public void setPeopleNumLimit(Integer peopleNumLimit) {
        this.peopleNumLimit = peopleNumLimit;
    }

    public Question withPeopleNumLimit(Integer peopleNumLimit) {
        this.peopleNumLimit = peopleNumLimit;
        return this;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Question withGrade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Question withStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Question withEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Question withStatus(String status) {
        this.status = status;
        return this;
    }

    public String DateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.TAIWAN); // example
        return sdf.format(date);
    }
    //顯示簡易的時間
    public String DateToSimpleString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd hh:mm", Locale.TAIWAN); // example
        return sdf.format(date);
    }
    //顯示簡易的時間
    public String DateToSimpleString(String date){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd hh:mm", Locale.TAIWAN); // example

        try {
            Date date1 = format.parse(date);//先轉Date
            return sdf.format(date1);//再轉簡易 Date String
        }catch (Exception e ){
            e.printStackTrace();
            return null;
        }
    }

    public Date DateConvert(String date){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        try {
            return format.parse(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}