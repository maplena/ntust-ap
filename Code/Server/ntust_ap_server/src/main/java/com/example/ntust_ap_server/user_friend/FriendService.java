package com.example.ntust_ap_server.user_friend;

import com.example.ntust_ap_server.edit_user.LoginCheckItem;
import com.example.ntust_ap_server.registration_user.appuser.AppUser;
import com.example.ntust_ap_server.registration_user.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendService {
    private final AppUserRepository appUserRepository;

    private final FriendRepository friendRepository;
    private final FriendListRepository friendListRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;// 加密 Password 用的物件
//    private final String URL = "jdbc:mariadb://localhost:3307/registration?serverTimezone=UTC";
//    private final String DB_USER = "root";
//    private final String DB_PASSWORD = "At29093711";

    public String send_firend_apply(FriendChangeItem friendChangeItem) {

        AppUser sender = appUserRepository.findByEmail(friendChangeItem.getEmail()).get();
        boolean loginSucessOrNot = LoginCheck(sender, friendChangeItem.getPassword());

        if (loginSucessOrNot) { //登入驗證

            try {
                AppUser receiver = appUserRepository.findByStudentid(friendChangeItem.getStudentid()).get();

                try {
                    if (friendListRepository.findByIdUserAndIdfriend(sender.getIduser(), receiver.getIduser())!=null)
                    return ("你們已經是朋友，不可發送邀請");
                }catch (Exception e){
                    System.out.println("尋找是否為朋友時發生錯誤");
                }


                if (sender.getStudentid().equals(receiver.getStudentid()))
                    return "不可發送給自己";

                FriendApplying input = new FriendApplying(sender.getIduser(), receiver.getIduser());

                try {
                    //如果寄送方寄送過申請
                    if (friendRepository.findByIduserAndIdfriendapplying(sender.getIduser(), receiver.getIduser()).get()!=null)
                        return "已發送過交友邀請";
                }catch (Exception e) {
                    System.out.println("尋找寄送者是否申請過時發生錯誤");
                }

                try {
                    //若該寄送者未建立 「個人好友列 table」 則自動建置，新增好友並相衝好友申請
                    if (friendRepository.findByIduserAndIdfriendapplying(receiver.getIduser(), sender.getIduser()).get()!=null) {
                        System.out.println("交友邀請相衝");
                        FriendListApplyClash(sender.getIduser(), receiver.getIduser());
                        return "接受對方好友邀請";
                    }
                }catch (Exception e){
                    System.out.println("尋找接收者是否向寄送者發送好友邀請時發生錯誤");
                }

                friendRepository.save(input);
                return "申請已寄送";

            }catch (Exception e){
                return "receiver 資料拿取失敗:"+e;
            }
        }else {
            return "帳號驗證出錯";
        }
    }

    public List<FriendApplying> get_firend_apply(LoginCheckItem loginCheckItem){
        AppUser temp = appUserRepository.findByEmail(loginCheckItem.getEmail()).get();
        boolean loginCheck = LoginCheck(temp,loginCheckItem.getPassword());

        if (loginCheck){
            System.out.println(temp.getIduser()+"OWO");
            return friendRepository.findByIdfriendapplying(temp.getIduser());
        }
        return null;
    }

    public List<FriendList> get_firendlist(LoginCheckItem loginCheckItem) throws SQLException {
        AppUser temp = appUserRepository.findByEmail(loginCheckItem.getEmail()).get();
        boolean loginCheck = LoginCheck(temp,loginCheckItem.getPassword());

        if (loginCheck){
//            return GetFriendList(temp.getIduser());
//            List<FriendList> tempList = friendListRepository.findAllByIdUser(temp.getIduser().toString());
            return friendListRepository.findByIdUser(temp.getIduser());
        }
        return null;
    }

    public String del_firend(DelFriendItem delFriendItem) throws SQLException {
        AppUser temp = appUserRepository.findByEmail(delFriendItem.getEmail()).get();
        boolean loginCheck = LoginCheck(temp,delFriendItem.getPassword());
        AppUser target_id = appUserRepository.findByStudentid(delFriendItem.getTarget_id()).get();
        if (loginCheck){
            return DelFirend(temp.getIduser(), target_id.getIduser());
        }
        return "登入驗證失敗";
    }


    private boolean LoginCheck(AppUser temp, String password){
        if (bCryptPasswordEncoder.matches(password,temp.getPassword()))
            return true;
        else return false;
    }

    private boolean FriendListApplyClash(Long senderId, Long receiverId) throws SQLException {

        friendListRepository.save(new FriendList(senderId,receiverId));
        friendListRepository.save(new FriendList(receiverId,senderId));
        FriendApplying tempApply = friendRepository.findFriendApplyingByIduserAndIdfriendapplying(receiverId,senderId);
        friendRepository.delete(tempApply);
//        friendRepository.delete(new FriendApplying(receiverId,senderId));
        return true;
    }

    private String DelFirend(Long iduser, Long target_id) throws SQLException {

        boolean friendOrNot = false;
        String return_use = "未找到此朋友id";

        if (friendListRepository.findByIdUserAndIdfriend(iduser, target_id)!=null){
            friendOrNot = true;
        }

        if (friendOrNot){
            friendListRepository.deleteByIdUserAndIdfriend(iduser, target_id);
            friendListRepository.deleteByIdUserAndIdfriend(target_id, iduser);
            return "刪除成功";
        }

        return return_use;
    }

    public String del_friend_apply(FriendChangeItem friendChangeItem) {
        AppUser sender = appUserRepository.findByEmail(friendChangeItem.getEmail()).get();
        boolean loginSucessOrNot = LoginCheck(sender, friendChangeItem.getPassword());

        if (loginSucessOrNot) { //登入驗證

            try {
                AppUser receiver = appUserRepository.findByStudentid(friendChangeItem.getStudentid()).get();

                try {
                    if (friendListRepository.findByIdUserAndIdfriend(sender.getIduser(), receiver.getIduser())!=null)
                        return ("你們已經是朋友，不可發送邀請");
                }catch (Exception e){
                    System.out.println("尋找是否為朋友時發生錯誤");
                }


                if (sender.getStudentid().equals(receiver.getStudentid()))
                    return "不可發送給自己";

                FriendApplying input = new FriendApplying(sender.getIduser(), receiver.getIduser());


                try {
                    if (friendRepository.findByIduserAndIdfriendapplying(receiver.getIduser(), sender.getIduser()).get()!=null) {
                        System.out.println("交友邀請相衝");
                        FriendListApplyDel(sender.getIduser(), receiver.getIduser());
                        return "申請已刪除";
                    }
                }catch (Exception e){
                    System.out.println("尋找接收者是否向寄送者發送好友邀請時發生錯誤");
                }

                friendRepository.save(input);
                return "申請已刪除";

            }catch (Exception e){
                return "receiver 資料拿取失敗:"+e;
            }
        }else {
            return "帳號驗證出錯";
        }
    }


    private boolean FriendListApplyDel(Long senderId, Long receiverId) throws SQLException {
        FriendApplying tempApply = friendRepository.findFriendApplyingByIduserAndIdfriendapplying(receiverId,senderId);
        friendRepository.delete(tempApply);
//        friendRepository.delete(new FriendApplying(receiverId,senderId));
        return true;
    }
}
