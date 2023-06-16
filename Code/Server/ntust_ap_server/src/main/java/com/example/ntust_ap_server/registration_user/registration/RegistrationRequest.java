package com.example.ntust_ap_server.registration_user.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// 2021.11.17 Boyan Start And End All

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

// Registration 物件
public class RegistrationRequest {
    private final String student_id;
    private final String name;
    private final String email;
    private final Boolean gender;
    private final String password;
    private final String school;
//<<<<<<< HEAD
//}
//=======

    /*get method */
    public String getStudent_id() {
        return this.student_id;
    }
    public String getName(){
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
    public Boolean getGender() {
        return this.gender;
    }
    public String getPassword(){
        return this.password;
    }
    public String getSchool(){
        return this.school;
    }

}
//>>>>>>> 513d16ad0b62677ed30555de7a2709770b74333a
