package com.example.ntust_ap_server.registration_user.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

// 2021.11.17 Boyan Start And End All

@Service

// 過濾 email 是否符合規則
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        return true;
    }
}
