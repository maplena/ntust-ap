package com.example.ntust_ap_server.registration_user.appuser;

import com.example.ntust_ap_server.registration_user.registration.token.ConfirmationToken;
import com.example.ntust_ap_server.registration_user.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

// 2021.11.17 Boyan Start And End All

// AppUser服務存取
@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;// 加密 Password 用的物件
    private final ConfirmationTokenService confirmationTokenService;// 驗證表單的服務

   
    @Override
    public UserDetails loadUserByUsername(String email) // 登入但找不到該 Email 傳回例外狀況
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    // 重設密碼的方法
    public String resetUserPassword(String email) {
        // 確認帳號存在
        boolean userExists = appUserRepository.findByEmail(email).isPresent();

        // 沒這個帳號跳進例外狀況
        if (!userExists) {
            throw new IllegalStateException("No one use this email / " + "email:" + email);
        }

        // 拿來存目標帳號資料
        AppUser targetUser = new AppUser();

        // 嘗試拿存取資料 (以電子郵件查找)
        try {
            targetUser = appUserRepository.findByEmail(email).get();
        } catch (Exception e) {
            throw new IllegalStateException("Can't get email: " + email + " data");
        }

        // random 出新的 token
        String token = UUID.randomUUID().toString();
        // 更新該 AppUser 的 token
        confirmationTokenService.updateToken(targetUser.getIduser(), token);
        // 更新該 token 許可期限
        confirmationTokenService.setExpiresAt(targetUser.getIduser());
        return token;
    }

    // 創建帳號用的方法
    public String signUpUser(AppUser appUser) {
        //電子郵件一律為小寫
        appUser.setEmail(appUser.getEmail().toLowerCase(Locale.ROOT));
        // 確認帳號存在
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        // 拿來存目標帳號資料
        AppUser targetUser = new AppUser();

        try {
            targetUser = appUserRepository.findByEmail(appUser.getEmail()).get();
        } catch (Exception e) {

        }

        // 如果該申請帳號已存在會更新 token 限制期限
        if (userExists) {

            // 如果尚未被驗證則重傳驗證信
            if (!targetUser.isEnabled()) {
                String token = UUID.randomUUID().toString();
                confirmationTokenService.updateToken(targetUser.getIduser(), token);
                confirmationTokenService.setExpiresAt(targetUser.getIduser());
                return token;
            } else {
                throw new IllegalStateException(appUser.getEmail() + "已被使用");
            }
        }

        if (appUser.getEmail().contains("ntu.")){
            appUser.setSchool("1");
        }else if (appUser.getEmail().contains("ntust.")){
            appUser.setSchool("2");
        }else if (appUser.getEmail().contains("ntnu.")){
            appUser.setSchool("3");
        }

        String splitEmailStr[] = appUser.getEmail().split("@");
        appUser.setStudentid(splitEmailStr[0]);

        // 加密 Password 用的物件
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        // 將加密過的密碼存入
        appUser.setPassword(encodedPassword);

        // 存取此 appUser
        appUserRepository.save(appUser);

        // random 出新的 token
        String token = UUID.randomUUID().toString();

        // 初始化新的驗證 Token，設定有效期限為 15min
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), appUser);

        // 存取此 token
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    // 驗證信 Query
    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    // 重設密碼 Query
    public int resetAppUserPassword(String password, String email) {
        return appUserRepository.resetAppUserPassword(password, email);
    }

    // 更新密碼 Query
    public int updateAppUserPassword(String password, String email) {
        return appUserRepository.resetAppUserPassword(password, email);
    }
}
