package com.example.figma.api;

public class UpdatePassword {
    private String email, password, updatePassword;

    public UpdatePassword(String email, String password, String updatePassword) {
        this.email = email;
        this.password = password;
        this.updatePassword = updatePassword;
    }
}
