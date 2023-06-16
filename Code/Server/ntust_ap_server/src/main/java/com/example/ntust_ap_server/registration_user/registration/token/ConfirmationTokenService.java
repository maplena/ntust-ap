package com.example.ntust_ap_server.registration_user.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

// 2021.11.17 Boyan Start And End All

// ConfirmationToken服務存取
@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    // 存取 token
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    // 取得鍵值為 token 的 ConfirmationToken 物件
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    // 更新 token 已驗證的時間 Query
    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    // 更新 token 到期的時間 Query
    public int setExpiresAt(long id) {
        return confirmationTokenRepository.updateExpiresAt(
                id, LocalDateTime.now().plusMinutes(15));
    }

    // 更新 token (重寄驗證信用)
    public int updateToken(long id, String token){
        return confirmationTokenRepository.updateTokenAt(id, token);
    }
}
