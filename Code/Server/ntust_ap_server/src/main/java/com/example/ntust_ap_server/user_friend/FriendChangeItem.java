package com.example.ntust_ap_server.user_friend;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FriendChangeItem {
    private final String email, password,studentid;
}
