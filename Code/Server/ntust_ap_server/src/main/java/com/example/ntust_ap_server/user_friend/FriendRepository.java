package com.example.ntust_ap_server.user_friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<FriendApplying, Long> {
    Optional<FriendApplying> findByIduserAndIdfriendapplying(Long iduser, Long idfriendapplying);

    List<FriendApplying> findByIdfriendapplying(Long iduser);
    FriendApplying findFriendApplyingByIduserAndIdfriendapplying(Long iduser, Long idfriendapplying);
}
