package com.example.ntust_ap_server.registration_user.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

// 2021.11.17 Boyan Start And End All

@Repository
@Transactional(readOnly = true)

// 存取ConfirmationTokenJPA
public interface ConfirmationTokenRepository
        extends JpaRepository<ConfirmationToken, Long> {

    // 已 token 為鍵值查詢
    Optional<ConfirmationToken> findByToken(String token);

    // 點選郵件中的驗證連結後，更新 token 確認時間
    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);

    // 更新 token
    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.token = ?2 " +
            "WHERE c.appUser.iduser = ?1")
    int updateTokenAt(Long id,
                          String token);

    // 更新 token 到期時間
    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.expiresAt = ?2 " +
            "WHERE c.appUser.iduser = ?1")
    int updateExpiresAt(Long id,
                          LocalDateTime expiresAt);
}
