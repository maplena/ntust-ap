package com.example.ntust_ap_server.edit_user;

// 2021.12.11 Boyan Start End

import com.example.ntust_ap_server.registration_user.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/edit_user") // 設定 URL path
@AllArgsConstructor
//使用者資料修改控制
public class EditUserController {

    private final EditUserService editUserService;

    //取得私人資料
    @PostMapping("/get_user_private")
    public AppUser get_user_private(@RequestBody LoginCheckItem editUserItem){
        return editUserService.get_user_private(editUserItem);
    }
    //取得公開資訊
//    @PostMapping("/get_user_public")
//    public PublicUserDataItem get_user_public(@RequestParam("email") String email){
//        return editUserService.get_user_public(email);
//    }
    @PostMapping("/get_user_public")
    public PublicUserDataItem get_user_public(@RequestParam("iduser") String iduser){
        return editUserService.get_user_public(iduser);
    }
    @PostMapping("/get_user_public_bystudentid")
    public PublicUserDataItem get_user_public_bystudentid(@RequestParam("studentid") String studentid){
        return editUserService.get_user_public_bystudentid(studentid);
    }
    @PostMapping("/get_user_public_byname")
    public PublicUserDataItem[] get_user_public_byname(@RequestParam("name") String name){
        return editUserService.get_user_public_byname(name);
    }

    //個資更新
    @PostMapping("/update_user_data")
    public EditableItem update_user_data(@RequestBody EditableItem editableItem){
        return editUserService.update_user_data(editableItem);
    }

    //個人照片地址更新
    @PostMapping("/update_user_image_path")
    public String update_user_image(@RequestBody UpdateUserImageItem updateUserImageItem){
        return editUserService.update_user_image_path(updateUserImageItem);
    }
}
