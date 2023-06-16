package com.example.ntust_ap_server.registration_user.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

// 2021.11.17 Boyan Start And End All

@Service
@AllArgsConstructor

// email 寄送服務
public class EmailService implements EmailSender{
    // 紀錄 EmailService 日誌
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    // 實現信件發送
    private final JavaMailSender mailSender;

    @Override
    @Async

    // 建至郵件並發送
    public void send(String to, String email) {
        try {
            // 整封郵件
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // 改編碼方式
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {

            // 寄送失敗
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }
}
