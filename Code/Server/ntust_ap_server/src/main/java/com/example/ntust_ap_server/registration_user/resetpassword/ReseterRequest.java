package com.example.ntust_ap_server.registration_user.resetpassword;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// 2021.11.17 Boyan Start And End All

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

// 重設密碼用物件
public class ReseterRequest {
    private final String email;
}
