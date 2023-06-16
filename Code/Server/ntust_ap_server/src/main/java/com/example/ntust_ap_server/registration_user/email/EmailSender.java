package com.example.ntust_ap_server.registration_user.email;

// 2021.11.17 Boyan Start And End All

public interface EmailSender {
    void send(String to, String email);
}