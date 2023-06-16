package com.example.figma;

import android.graphics.Bitmap;

public class Personal {
    private Bitmap personal_image;
    private String personal_number;
    private String personal_name;
    private String personal_tag;
    private String personal_sex;

    public Personal(Bitmap personal_image,String personal_number,String personal_name,String personal_tag,Boolean sex){
        this.personal_image = personal_image;
        this.personal_number = personal_number;
        this.personal_name = personal_name;
        this.personal_tag = personal_tag;
        if(sex){
            personal_sex = "男";
        }
        else {
            personal_sex = "女";
        }
    }

    public Bitmap getPersonal_image() {
        return personal_image;
    }

    public String getPersonal_number() {
        return personal_number;
    }

    public String getPersonal_name() {
        return personal_name;
    }

    public String getPersonal_tag() {
        return personal_tag;
    }

    public String getPersonal_sex() {
        return personal_sex;
    }

    public void setPersonal_image(Bitmap personal_image_path) {
        this.personal_image = personal_image_path;
    }

    public void setPersonal_number(String personal_number) {
        this.personal_number = personal_number;
    }

    public void setPersonal_name(String personal_name) {
        this.personal_name = personal_name;
    }

    public void setPersonal_tag(String personal_tag) {
        this.personal_tag = personal_tag;
    }

}
