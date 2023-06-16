package com.example.figma;

public class Notification {
    private String event_name;
    private String[] event_tag;
    private String event_time;

    public Notification(String event_name,String[] event_tag,String event_time){
        this.event_name = event_name;
        this.event_tag = event_tag;
        this.event_time = event_time;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String[] getEvent_tag() {
        return event_tag;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setEvent_tag(String[] event_tag) {
        this.event_tag = event_tag;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }
}
