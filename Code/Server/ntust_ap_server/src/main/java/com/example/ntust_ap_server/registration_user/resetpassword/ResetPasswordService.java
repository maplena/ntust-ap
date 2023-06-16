package com.example.ntust_ap_server.registration_user.resetpassword;

import com.example.ntust_ap_server.edit_user.EditUserRepository;
import com.example.ntust_ap_server.registration_user.appuser.AppUser;
import com.example.ntust_ap_server.registration_user.appuser.AppUserService;
import com.example.ntust_ap_server.registration_user.email.EmailSender;
import com.example.ntust_ap_server.registration_user.registration.EmailValidator;
import com.example.ntust_ap_server.registration_user.registration.RandomPassword;
import com.example.ntust_ap_server.registration_user.registration.RegistrationRequest;
import com.example.ntust_ap_server.registration_user.registration.token.ConfirmationToken;
import com.example.ntust_ap_server.registration_user.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

// 2021.11.17 Boyan Start And End All

@Service
@AllArgsConstructor

// ResetPassword服務存取
public class ResetPasswordService {
    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    private final EditUserRepository editUserRepository;
    private final EmailSender emailSender;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 進入註冊 url 的位置 ("api/v1/resetPassword")
    public String reseter(RegistrationRequest request) {

        // 看該 email 是否符合標準
        boolean isValidEmail = emailValidator.test(request.getEmail());

        // email 不合標準
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        // 更新該 appuser 的 token
        String token = appUserService.resetUserPassword(request.getEmail());

        // 隨機出一個密碼
        String password = RandomPassword.randomPassword();

        // 加密
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        // 設定 link
        String link = "http://localhost:8080/api/v1/resetPassword/confirm?token=" + token + "&&" + "password="
                + encodedPassword;
        emailSender.send(request.getEmail(), buildEmail(request.getEmail(), link, password));

        return token;
    }

    // 進入註冊 url 的位置 ("api/v1/resetPassword/update")
    public String update_password(PasswordUpdaterRequest request) {

        // 看該 email 是否符合標準
        boolean isValidEmail = emailValidator.test(request.getEmail());
        Boolean checkChange = false;

        // email 不合標準
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        AppUser temp = editUserRepository.findByEmail(request.getEmail()).get();

        if (bCryptPasswordEncoder.matches(request.getPassword(),temp.getPassword())){
            checkChange = true;
        } else{
            return null;
        }

        if (checkChange){
            // 加密
            String encodedPassword = bCryptPasswordEncoder.encode(request.getUpdatePassword());
            appUserService.updateAppUserPassword(encodedPassword, request.getEmail());
            return  "密碼更新成功";
        }else {
            return "合法性驗證失敗";
        }
    }

    @Transactional
    // 可查看 RegistrationService.confirmToken 註釋
    public String confirmToken(String token, String password) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        confirmationTokenService.setConfirmedAt(token);

        appUserService.resetAppUserPassword(password, confirmationToken.getAppUser().getEmail());
        return "confirmed password";
    }

    // 可查看 RegistrationService.buildEmail 註釋
    private String buildEmail(String name, String link, String password) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" + "\n"
                + "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" + "\n"
                + "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "    <tbody><tr>\n" + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" + "        \n"
                + "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
                + "          <tbody><tr>\n" + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
                + "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                + "                  <tbody><tr>\n" + "                    <td style=\"padding-left:10px\">\n"
                + "                  \n" + "                    </td>\n"
                + "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
                + "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n"
                + "                    </td>\n" + "                  </tr>\n" + "                </tbody></table>\n"
                + "              </a>\n" + "            </td>\n" + "          </tr>\n" + "        </tbody></table>\n"
                + "        \n" + "      </td>\n" + "    </tr>\n" + "  </tbody></table>\n"
                + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                + "    <tbody><tr>\n" + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
                + "      <td>\n" + "        \n"
                + "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                + "                  <tbody><tr>\n"
                + "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
                + "                  </tr>\n" + "                </tbody></table>\n" + "        \n" + "      </td>\n"
                + "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" + "    </tr>\n"
                + "  </tbody></table>\n" + "\n" + "\n" + "\n"
                + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                + "    <tbody><tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "    <tr>\n"
                + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
                + "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
                + "        \n"
                + "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name
                + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Your reset password is "
                + password
                + ". Please click on the below link to reset your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
                + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>"
                + "        \n" + "      </td>\n" + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
                + "    </tr>\n" + "    <tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n"
                + "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" + "\n" + "</div></div>";
    }
}
