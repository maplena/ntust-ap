package com.example.figma;

import com.example.figma.Eventtest.EventItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event {
    private Integer event_id;
    private String event_name;
    private String event_description;
    private String event_image_path;

    private String event_host;
    private String event_hostID;
    private String event_host_email;
    private String event_host_phone;

    private String event_school;
    private String event_place;
    private String event_tag;
    private String event_type;

    private Integer event_school_limit;
    private Integer event_gender;
    private Integer max_people;
    private Integer event_price;
    private Integer event_meal;
    private Integer event_grade;

    private Date event_open_date;//報名開始時間
    private Date event_deadline_date;//報名結束時間
    private Date event_start_date;//活動開始時間
    private Date event_end_date;//活動結束時間
    private String event_state;

    private Integer now_people;//當前參加人數





    public Event(Integer event_id,String event_name,String event_description,String event_image_path,String event_host,String event_hostID,String event_host_email,String event_host_phone,String event_school,String event_place,String event_tag,String event_type,Integer event_school_limit,Integer event_gender,Integer max_people,Integer event_price,Integer event_meal,Integer event_grade,Date event_open_date,Date event_deadline_date,Date event_start_date,Date event_end_date,String event_state){
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_description = event_description;
        this.event_image_path = event_image_path;

        this.event_host = event_host;
        this.event_hostID = event_hostID;
        this.event_host_email = event_host_email;
        this.event_host_phone = event_host_phone;

        this.event_school = event_school;
        this.event_place = event_place;
        this.event_tag = event_tag;
        this.event_type = event_type;

        this.event_school_limit = event_school_limit;
        this.event_gender = event_gender;
        this.max_people = max_people;
        this.event_price = event_price;
        this.event_meal = event_meal;
        this.event_grade = event_grade;

        this.event_open_date = event_open_date;
        this.event_deadline_date = event_deadline_date;
        this.event_start_date = event_start_date;
        this.event_end_date = event_end_date;
        this.event_state = event_state;
    }

    public Event(String event_name,String event_description,String event_image_path,String event_host,String event_hostID,String event_host_email,String event_host_phone,String event_school,String event_place,String event_tag,String event_type,Integer event_school_limit,Integer event_gender,Integer max_people,Integer event_price,Integer event_meal,Integer event_grade,Date event_open_date,Date event_deadline_date,Date event_start_date,Date event_end_date,String event_state){
        this.event_name = event_name;
        this.event_description = event_description;
        this.event_image_path = event_image_path;

        this.event_host = event_host;
        this.event_hostID = event_hostID;
        this.event_host_email = event_host_email;
        this.event_host_phone = event_host_phone;

        this.event_school = event_school;
        this.event_place = event_place;
        this.event_tag = event_tag;
        this.event_type = event_type;

        this.event_school_limit = event_school_limit;
        this.event_gender = event_gender;
        this.max_people = max_people;
        this.event_price = event_price;
        this.event_meal = event_meal;
        this.event_grade = event_grade;

        this.event_open_date = event_open_date;
        this.event_deadline_date = event_deadline_date;
        this.event_start_date = event_start_date;
        this.event_end_date = event_end_date;
        this.event_state = event_state;
    }

    public Event(EventItem event){
        this.event_id = event.getId();
        this.event_name = event.getTitle();
        this.event_description = event.getDescription();
        this.event_image_path = event.getImagePath();

        this.event_host = event.getHost();
        this.event_hostID = event.getHostID();
        this.event_host_email = event.getHostEmail();
        this.event_host_phone = event.getHostPhoneNumber();

        this.event_school = event.getSchool();
        this.event_place = event.getPlace();
        this.event_tag = event.getTag();
        this.event_type = event.getType();

        this.event_school_limit = event.getSchoolLimit();
        this.event_gender = event.getGender();
        this.max_people = event.getPeopleNumLimit();
        this.event_price = event.getPrice();
        this.event_meal = event.getMeal();
        this.event_grade = event.getGrade();
        this.now_people = event.getNow_people();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        try {
            this.event_open_date = format.parse(event.getOpenDate());
            this.event_deadline_date = format.parse(event.getDeadlineDate());
            this.event_start_date = format.parse(event.getStartDate());
            this.event_end_date = format.parse(event.getEndDate());
        }catch (Exception e){
            e.printStackTrace();
        }


    }



    public Integer getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_description() {
        return event_description;
    }

    public String getEvent_host() {
        return event_host;
    }

    public String getEvent_hostID() {
        return event_hostID;
    }

    public String getEvent_host_email() {
        return event_host_email;
    }

    public String getEvent_host_phone() {
        return event_host_phone;
    }

    public String getEvent_school() {
        return event_school;
    }

    public String getEvent_place() {
        return event_place;
    }

    public String getEvent_tag() {
        return event_tag;
    }

    public String getEvent_type() {
        return event_type;
    }

    public Integer getEvent_school_limit() {
        return event_school_limit;
    }

    public Integer getEvent_gender() {
        return event_gender;
    }

    public Integer getMax_people() {
        return max_people;
    }

    public Integer getEvent_price() {
        return event_price;
    }

    public Integer getEvent_meal() {
        return event_meal;
    }

    public Integer getEvent_grade() {
        return event_grade;
    }

    public Date getEvent_open_date() {
        return event_open_date;
    }

    public Date getEvent_deadline_date() {
        return event_deadline_date;
    }

    public Date getEvent_start_date() {
        return event_start_date;
    }

    public Date getEvent_end_date() {
        return event_end_date;
    }

    public String getEvent_state() {
        return event_state;
    }

    public Integer getNow_people() {
        return now_people;
    }

    public void setNow_people(Integer now_people) {
        this.now_people = now_people;
    }

    public String DateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.TAIWAN); // example
        return sdf.format(date);
    }
    public String DateToSimpleString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd hh:mm", Locale.TAIWAN); // example
        return sdf.format(date);
    }
}
