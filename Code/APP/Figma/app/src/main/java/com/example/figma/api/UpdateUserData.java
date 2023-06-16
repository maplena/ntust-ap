package com.example.figma.api;

public class UpdateUserData {
    private String email, name, password, relationship, image_path, self_info, interest, tag;
    private Boolean gender;

    public UpdateUserData(String email, String name, String password,
                          String relationship, Boolean gender, String image_path,
                          String self_info, String interest, String tag) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.relationship = relationship;
        this.gender = gender;
        this.image_path = image_path;
        this.self_info = self_info;
        this.interest = interest;
        this.tag = tag;
    }
}
