package com.example.figma.api;

public class RegistrationUser {
    private String student_id,email,name,password,school;
    private boolean gender;

    public RegistrationUser(String student_id, String email, String name, String password, boolean gender, String school) {
        this.student_id = student_id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.school = school;
    }
}
