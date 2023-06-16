package com.example.ntust_ap_server.user_friend;

import com.example.ntust_ap_server.edit_user.LoginCheckItem;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/friend") // 設定 URL path
@AllArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/send_friend_apply")
    public String send_friend_apply(@RequestBody FriendChangeItem friendChangeItem){
        return friendService.send_firend_apply(friendChangeItem);
    }

    @PostMapping("/del_friend_apply")
    public String del_friend_apply(@RequestBody FriendChangeItem friendChangeItem){
        return friendService.del_friend_apply(friendChangeItem);
    }

    @PostMapping("/get_friend_apply")
    public List<FriendApplying> get_friend_apply(@RequestBody LoginCheckItem loginCheckItem){
        return friendService.get_firend_apply(loginCheckItem);
    }

    @PostMapping("/get_friendlist")
    public List<FriendList> get_friendlist(@RequestBody LoginCheckItem loginCheckItem) throws SQLException {
        return friendService.get_firendlist(loginCheckItem);
    }

    @PostMapping("/del_friend")
    public String del_friend(@RequestBody DelFriendItem delFriendItem) throws SQLException {
        return friendService.del_firend(delFriendItem);
    }
}
