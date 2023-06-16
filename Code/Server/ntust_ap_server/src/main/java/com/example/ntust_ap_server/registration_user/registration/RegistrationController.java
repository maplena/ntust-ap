package com.example.ntust_ap_server.registration_user.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

// 2021.11.17 Boyan Start And End All

@RestController
@RequestMapping(path = "api/v1/registration") // 設定 URL path
@AllArgsConstructor

// 控制 registration 下的  mapping
public class RegistrationController {

    private final RegistrationService registrationService;

    // 進入點，在進入後建置帳號並寄送郵件
    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    // 進入後驗證 token 狀態 (confirmed/token 找不到、已驗證、過期)
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
