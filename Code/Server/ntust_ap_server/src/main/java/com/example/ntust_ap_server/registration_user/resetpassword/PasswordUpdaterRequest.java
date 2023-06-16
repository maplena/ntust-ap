package com.example.ntust_ap_server.registration_user.resetpassword;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class PasswordUpdaterRequest {
    private final String email;
    private final String password;
    private final String updatePassword;
}
