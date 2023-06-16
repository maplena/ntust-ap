package com.example.ntust_ap_server.edit_user;

// 2021.12.11 Boyan Start End

import com.example.ntust_ap_server.registration_user.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EditUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email); // 以 email 為鍵值搜尋
    Optional<AppUser> findByIduser(Long Iduser); // 以 Iduser 為鍵值搜尋

    Optional<AppUser[]> findAllByStudentid(String studentid); // 以 studentid 為鍵值搜尋
    Optional<AppUser> findByStudentid(String studentid); // 以 studentid 為鍵值搜尋
    Optional<AppUser> findFirstByName(String name); // 以 name 為鍵值搜尋
    Optional<AppUser[]> findAllByName(String name); // 以 name 為鍵值搜尋
    //更新使用者資訊
    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.name = ?2,a.tag = ?3,a.self_info = ?4,a.gender = ?5, a.relationship = ?6, a.image_path = ?7 ,a.interest = ?8 " +
            "WHERE a.email = ?1")
    int updateAppUser(String email, String name, String tag,
                      String self_info, boolean gender, int relationship, String image_path, String interest);

    //更新使用者照片地址
    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.image_path = ?2 WHERE a.email = ?1")
    int updateAppUserImage(String email, String image_path);
}
