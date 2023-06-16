package com.example.ntust_ap_server.registration_user.registration;

import com.example.ntust_ap_server.registration_user.appuser.AppUser;
import com.example.ntust_ap_server.registration_user.appuser.AppUserRole;
import com.example.ntust_ap_server.registration_user.appuser.AppUserService;
import com.example.ntust_ap_server.registration_user.email.EmailSender;
import com.example.ntust_ap_server.registration_user.registration.token.ConfirmationToken;
import com.example.ntust_ap_server.registration_user.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

// 2021.11.17 Boyan Start And End All

@Service
@AllArgsConstructor

// Registration服務存取
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    // 進入註冊 url 的位置 ("api/v1/registration")
    public String register(RegistrationRequest request) {

        // 看該 email 是否符合標準
        boolean isValidEmail = emailValidator.
                test(request.getEmail());

        // email 不合標準
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String emailSplit[] = request.getEmail().split("@");

        if (!(emailSplit[1].matches("ntu.edu.tw") ||
                emailSplit[1].matches("mail.ntust.edu.tw") ||
                emailSplit[1].matches("ntnu.edu.tw"))){
            throw new IllegalStateException("請使用下列格式註冊:@mail.ntust.edu.tw\n/@ntu.edu.tw/@ntnu.edu.tw");
        }
        // 創建帳號並會回傳該 appuser 的 token
        String token = appUserService.signUpUser(
                new AppUser(
                        request.getStudent_id(),
                        request.getName(),
                        request.getEmail(),
                        request.getGender(),
                        request.getPassword(),
                        AppUserRole.USER,
                        request.getSchool()
                )
        );

        // 設定 link
        String link = "http://140.118.110.213:8080/api/v1/registration/confirm?token=" + token;

        // 寄送 email
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getName(), link));

        return token;
    }

    @Transactional

    // 點選確認驗證 link 的進入點 ("api/v1/registration/confirm?token=" + token")
    public String confirmToken(String token) {

        // 找不找的到該 token
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        // 已認證過
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        // 更新到期時間
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        // 檢測過期
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        // 確認驗證
        confirmationTokenService.setConfirmedAt(token);

        // 更新 enable=true
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }

    // email html 檔案
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
