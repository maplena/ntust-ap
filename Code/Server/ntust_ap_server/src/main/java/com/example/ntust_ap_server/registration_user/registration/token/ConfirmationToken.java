package com.example.ntust_ap_server.registration_user.registration.token;

import com.example.ntust_ap_server.registration_user.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

// 2021.11.17 Boyan Start And End All

@Getter
@Setter
@NoArgsConstructor
@Entity

// Token 物件
public class ConfirmationToken {

    // token 自動序列化
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @Column(nullable = false)// 不可為空值
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;// 創建時間

    @Column(nullable = false)
    private LocalDateTime expiresAt;// 到期時間

    private LocalDateTime confirmedAt;// 確認時間

    // 設定為多對一
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "iduser"
    )

    private AppUser appUser;

    // 初始化
    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }

    public LocalDateTime getConfirmedAt() {
            return this.confirmedAt;
    }
}
