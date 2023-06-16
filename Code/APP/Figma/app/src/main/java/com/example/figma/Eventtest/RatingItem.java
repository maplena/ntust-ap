package com.example.figma.Eventtest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class RatingItem {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("activityId")
    @Expose
    private Long activityId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("rating")
    @Expose
    private Long rating;
    @SerializedName("comment")
    @Expose
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RatingItem withId(Long id) {
        this.id = id;
        return this;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public RatingItem withActivityId(Long activityId) {
        this.activityId = activityId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RatingItem withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public RatingItem withRating(Long rating) {
        this.rating = rating;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RatingItem withComment(String comment) {
        this.comment = comment;
        return this;
    }

}