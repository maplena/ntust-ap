package com.example.ntust_ap_server.user_friend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class FriendApplying {
    // 自動序列化
    @SequenceGenerator(
            name = "user_firend_applying",
            sequenceName = "user_firend_applying",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_firend_applying"
    )

    private Long id;

    @Column(nullable = false)// 不可為空值
    private Long iduser;

    @Column(nullable = false)
    private Long idfriendapplying;

    public FriendApplying(Long iduser, Long idfriendapplying){
        this.iduser = iduser;
        this.idfriendapplying = idfriendapplying;
    }

}
