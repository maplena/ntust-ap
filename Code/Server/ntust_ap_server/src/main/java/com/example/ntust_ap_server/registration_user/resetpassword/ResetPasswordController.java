package com.example.ntust_ap_server.registration_user.resetpassword;

import com.example.ntust_ap_server.registration_user.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.web.bind.annotation.*;

// 2021.11.17 Boyan Start And End All

@RestController
@RequestMapping(path = "api/v1/resetPassword")// 設定 URL path
@Getter
@AllArgsConstructor

// 控制 ResetPassword 下的  mapping
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    // 進入點，在進入後發送系統重設密碼郵件
    @PostMapping
    public String reseter(@RequestBody RegistrationRequest request) {
        return resetPasswordService.reseter(request);
    }

    // 進入點，檢查完合法性後允許更新密碼
    @PostMapping("/update")
    public String update_password(@RequestBody PasswordUpdaterRequest request) {
        return resetPasswordService.update_password(request);
    }

    // 進入後驗證 token 狀態 (confirmed/token 找不到、已驗證、過期)
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token, @RequestParam("password") String password) {
        return resetPasswordService.confirmToken(token, password);
    }

}
