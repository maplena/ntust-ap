package com.example.ntust_ap_server.registration_user.appuser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

// 2021.11.17 Boyan Start And End All

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="user") // 不設定會預設為 class 名

// AppUser(使用者物件)
public class AppUser implements UserDetails {
    // 紀錄id使用數
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1 // id間的差值
    )
    @Id // id自動生成
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )

    private Long iduser;

    private String studentid;
    private String name, email, password, school, tag, self_info, image_path, interest;
    private int relationship;
    private Boolean gender;
    @Enumerated(EnumType.STRING) // 身分enum
    private AppUserRole appUserRole;
    private Boolean locked = false; // 帳號鎖
    private Boolean enabled = false;// 郵件驗證後才會為 true

    // 初始化
    public AppUser(String studentid, // id為自動生成不放入其中
                   String name,
                   String email,
                   Boolean gender,
                   String password,
                   AppUserRole appUserRole,
                   String school) {
        this.studentid = studentid;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.appUserRole = appUserRole;
        this.studentid = studentid;
        this.school = school;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 返回授予用戶的權限，按自然鍵排序
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    // getter, setter
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() { return email; }

    public String getStudentid() {
        return studentid;
    }

    public String getName() {
        return name;
    }

    public String getImage_path() {
        return image_path;
    }

    public Boolean getGender() { return gender; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // 帳號未過期

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    } // 帳號是否某鎖住(預設未鎖)

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // 憑證否過期

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
