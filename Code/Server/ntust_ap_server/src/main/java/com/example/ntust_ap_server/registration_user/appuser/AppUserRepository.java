package com.example.ntust_ap_server.registration_user.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// 2021.11.17 Boyan Start And End All

@Repository
@Transactional(readOnly = true)

// 存取AppUserJPA
public interface AppUserRepository
        extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email); //以郵件為鍵值

    Optional<AppUser> findByStudentid(String student_id); // 以 student_id 為鍵值

    // 驗證信連結被點後，更新 enabled 欄位為 true (使用者才被允許登入)
    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    // 使用者點選忘記密碼並填入電子郵件後，會由系統生成密碼並以郵件告知。
    // 點選郵件內的連結後才會更新該密碼至資料庫。
    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.password = ?1 WHERE a.email = ?2")
    int resetAppUserPassword(String password,String email);

    // 帳號合法性檢查通過後允許更新密碼。
    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.password = ?1 WHERE a.email = ?2")
    int updateAppUserPassword(String password,String email);
}
