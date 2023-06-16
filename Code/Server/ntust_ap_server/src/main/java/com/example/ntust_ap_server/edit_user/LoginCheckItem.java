package com.example.ntust_ap_server.edit_user;

// 2021.12.11 Boyan Start End

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
//登入驗證物件
public class LoginCheckItem {
    private final String email, password;
}
