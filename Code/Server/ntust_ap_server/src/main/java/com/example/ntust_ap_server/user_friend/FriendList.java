package com.example.ntust_ap_server.user_friend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class FriendList {
    // 自動序列化
    @SequenceGenerator(
            name = "user_firend_sequence",
            sequenceName = "user_firend",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_firend"
    )

    private Long id;

    @Column(nullable = false)// 不可為空值
    private Long idUser;

    @Column(nullable = false)
    private Long idfriend;

    public FriendList(Long iduser, Long idfriend) {
        this.idUser = iduser;
        this.idfriend = idfriend;
    }
}
