package com.example.ntust_ap_server.user_friend;

// 2021.12.11 Boyan Start End

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
//登入驗證物件
public class DelFriendItem {
    private final String email, password, target_id;
}
