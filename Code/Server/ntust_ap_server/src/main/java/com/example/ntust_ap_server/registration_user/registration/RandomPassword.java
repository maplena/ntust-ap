package com.example.ntust_ap_server.registration_user.registration;

import java.util.Random;

// 2021.11.17 Boyan Start And End All

// 自動生成忘記密碼的系統預設密碼
public class RandomPassword {

    // 特殊字元使用
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

    // 隨機出八位
    public static String randomPassword() {
        char[] chars = new char[8];
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) {
            chars[i] = nextChar(rnd);
        }
        return new String(chars);
    }

    // 隨機出字元
    private static char nextChar(Random rnd) {
        switch (rnd.nextInt(4)) {
            case 0:
                return (char) ('a' + rnd.nextInt(26));
            case 1:
                return (char) ('A' + rnd.nextInt(26));
            case 2:
                return (char) ('0' + rnd.nextInt(10));
            default:
                return SPECIAL_CHARS.charAt(rnd.nextInt(SPECIAL_CHARS.length()));
        }
    }
}
