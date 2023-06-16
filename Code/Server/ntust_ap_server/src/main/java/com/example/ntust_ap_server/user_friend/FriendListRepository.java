package com.example.ntust_ap_server.user_friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Transactional
@Repository
public interface FriendListRepository extends JpaRepository<FriendList, Long> {
    FriendList findByIdUserAndIdfriend(Long iduser, Long Idfriend);

    List<FriendList> findByIdUser(Long iduser);
    List<FriendList> deleteByIdUserAndIdfriend(Long iduser, Long Idfriend);

}