package com.example.ntust_ap_server.security.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "sucess")// 設定 URL path
@Getter
@AllArgsConstructor

public class WebSecurityConfigController {
    @GetMapping
    public String loginsuccess() {
        return "Login Sucess";
    }
}

