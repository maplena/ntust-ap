package com.example.figma.Eventtest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;


@Generated("jsonschema2pojo")
public class EventItem {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;
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
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("school_limit")
    @Expose
    private Integer schoolLimit;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("people_num_limit")
    @Expose
    private Integer peopleNumLimit;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("meal")
    @Expose
    private Integer meal;
    @SerializedName("grade")
    @Expose
    private Integer grade;
    @SerializedName("open_date")
    @Expose
    private String openDate;
    @SerializedName("deadline_date")
    @Expose
    private String deadlineDate;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userDepartment")
    @Expose
    private String userDepartment;
    @SerializedName("userSchool")
    @Expose
    private Integer userSchool;
    @SerializedName("userGrade")
    @Expose
    private Integer userGrade;
    @SerializedName("userPhone")
    @Expose
    private String userPhone;
    @SerializedName("userMeal")
    @Expose
    private Integer userMeal;
    @SerializedName("now_people")
    @Expose
    private Integer now_people=0;

    public Integer getNow_people(){return now_people;}
    public void  setNow_people(Integer now_people){this.now_people=now_people;}

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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSchoolLimit() {
        return schoolLimit;
    }

    public void setSchoolLimit(Integer schoolLimit) {
        this.schoolLimit = schoolLimit;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getPeopleNumLimit() {
        return peopleNumLimit;
    }

    public void setPeopleNumLimit(Integer peopleNumLimit) {
        this.peopleNumLimit = peopleNumLimit;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMeal() {
        return meal;
    }

    public void setMeal(Integer meal) {
        this.meal = meal;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public Integer getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(Integer userSchool) {
        this.userSchool = userSchool;
    }

    public Integer getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(Integer userGrade) {
        this.userGrade = userGrade;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getUserMeal() {
        return userMeal;
    }

    public void setUserMeal(Integer userMeal) {
        this.userMeal = userMeal;
    }

}