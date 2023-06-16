package com.example.figma.Eventtest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ParticipentItem {

    @SerializedName("activityId")
    @Expose
    private Long activityId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userName")
    @Expose
    private String userName="";
    @SerializedName("school")
    @Expose
    private Long school;
    @SerializedName("gender")
    @Expose
    private Long gender;
    @SerializedName("grade")
    @Expose
    private Long grade;
    @SerializedName("phone")
    @Expose
    private Object phone="";
    @SerializedName("meal")
    @Expose
    private Long meal;
    @SerializedName("id")
    @Expose
    private Long id;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public ParticipentItem withActivityId(Long activityId) {
        this.activityId = activityId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ParticipentItem withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ParticipentItem withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public ParticipentItem withSchool(Long school) {
        this.school = school;
        return this;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public ParticipentItem withGender(Long gender) {
        this.gender = gender;
        return this;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public ParticipentItem withGrade(Long grade) {
        this.grade = grade;
        return this;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public ParticipentItem withPhone(Object phone) {
        this.phone = phone;
        return this;
    }

    public Long getMeal() {
        return meal;
    }

    public void setMeal(Long meal) {
        this.meal = meal;
    }

    public ParticipentItem withMeal(Long meal) {
        this.meal = meal;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParticipentItem withId(Long id) {
        this.id = id;
        return this;
    }

}