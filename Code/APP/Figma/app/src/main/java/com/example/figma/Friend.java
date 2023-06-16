package com.example.figma;

public class Friend {
    private int friend_userid;
    private String name, student_id, tag;
    private String image_path, school;
    private Boolean gender;

    public Friend(int friend_userid, String name, String student_id, String tag, String image_path, String school, Boolean gender) {
        this.friend_userid = friend_userid;
        this.name = name;
        this.student_id = student_id;
        this.tag = tag;
        this.image_path = image_path;
        this.school = school;
        this.gender = gender;
    }

    public int getFriend_userid() { return friend_userid; }

    public void setFriend_userid(int friend_userid) { this.friend_userid = friend_userid; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
}
