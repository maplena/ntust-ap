package com.example.figma;

import android.graphics.Bitmap;

public class Global {
    public static String studentid;
    public static String name, email, password, school, tag, self_info, image_path, interest;
    public static int relationship;
    public static Boolean gender;
    public static String tempF7_studentid;

    public static int userGrade, school_num;
    public static Bitmap bitmap;

    public static String convertToTag(){
        String schoolstr, genderstr, gradestr;
        if (school_num == 1){
            schoolstr = "台大";
        }else if(school_num == 2){
            schoolstr = "台科";
        }else {
            schoolstr = "台師大";
        }

        if (gender){
            genderstr = "男";
        }else {
            genderstr = "女";
        }

        if (userGrade == 1){
            gradestr = "大一";
        }else if (userGrade == 2){
            gradestr = "大二";
        }else if (userGrade == 3){
            gradestr = "大三";
        }else if (userGrade == 4){
            gradestr = "大四";
        }else {
            gradestr = "超過大四(OwO";
        }

        return "#"+schoolstr+"#"+genderstr+"#"+gradestr;
    }

    public static String temp_convertToTag(String temp_school, Boolean temp_gender, String temp_studentid){
        String schoolstr, genderstr, gradestr;
        int temp_userGrade = -1;
        int temp_school_num = Integer.parseInt(temp_school);

        if (temp_school_num == 1){
            schoolstr = "台大";
        }else if(temp_school_num == 2){
            schoolstr = "台科";
        }else {
            schoolstr = "台師大";
        }

        if (temp_gender){
            genderstr = "男";
        }else {
            genderstr = "女";
        }

        try {
            if (temp_school_num == 1){
                temp_userGrade = (11 - Integer.parseInt(temp_studentid.substring(1,3)));
            }else if (temp_school_num == 2){
                temp_userGrade = (111 - Integer.parseInt(temp_studentid.substring(1,4)));
            }
        }catch (Exception e){
            System.out.println("System : school_num or userGrade Global Failed");
        }

        if (temp_userGrade == 1){
            gradestr = "大一";
        }else if (temp_userGrade == 2){
            gradestr = "大二";
        }else if (temp_userGrade == 3){
            gradestr = "大三";
        }else if (temp_userGrade == 4){
            gradestr = "大四";
        }else {
            gradestr = "超過大四(OwO";
        }

        return "#"+schoolstr+"#"+genderstr+"#"+gradestr;
    }
}
