package com.example.ntust_ap_server.edit_user;

// 2021.12.11 Boyan Start End

import com.example.ntust_ap_server.registration_user.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//使用者資料修改服務
@Service
@AllArgsConstructor
public class EditUserService {

    private final EditUserRepository editUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;// 加密 Password 用的物件

    //取得私人資料
    public AppUser get_user_private(LoginCheckItem editUserItem) {
        AppUser temp = editUserRepository.findByEmail(editUserItem.getEmail()).get();

        if (bCryptPasswordEncoder.matches(editUserItem.getPassword(),temp.getPassword()))
            return temp;
        else return null;
    }

    //取得公開資料
//    public PublicUserDataItem get_user_public(String email) {
    public PublicUserDataItem get_user_public(String iduser){
        AppUser temp = new AppUser();
        try{
            temp = editUserRepository.findByIduser(Long.parseLong(iduser)).get();
        }catch (Exception e){
            System.out.println(iduser);
            System.out.println("Cant get AppUser");
        }

        //將部分私人資料壓入後再回傳
        PublicUserDataItem publicUserDataItem = new PublicUserDataItem(
                temp.getIduser(),
                temp.getStudentid(),
                temp.getName(),
                temp.getTag(),
                temp.getSelf_info(),
                temp.getInterest(),
                temp.getImage_path(),
                temp.getSchool(),
                temp.getGender(),
                temp.getRelationship()
        );

        return publicUserDataItem;
    }

    public PublicUserDataItem get_user_public_bystudentid(String studentid){
        AppUser temp = new AppUser();
        try{
            temp = editUserRepository.findByStudentid(studentid).get();
        }catch (Exception e){
            System.out.println(studentid);
            System.out.println("Cant get AppUser");
        }

        if (temp.getStudentid() == null){
            try{
                temp = editUserRepository.findFirstByName(studentid).get();
            }catch (Exception e){
                System.out.println(studentid);
                System.out.println("Cant get AppUser");
            }
        }

        //將部分私人資料壓入後再回傳
        PublicUserDataItem publicUserDataItem = new PublicUserDataItem(
                temp.getIduser(),
                temp.getStudentid(),
                temp.getName(),
                temp.getTag(),
                temp.getSelf_info(),
                temp.getInterest(),
                temp.getImage_path(),
                temp.getSchool(),
                temp.getGender(),
                temp.getRelationship()
        );

        return publicUserDataItem;
    }

    public PublicUserDataItem[] get_user_public_byname(String name){
        AppUser[] temp = new AppUser[]{};

        try{
            temp = editUserRepository.findAllByName(name).get();
        }catch (Exception e){
            System.out.println(name);
            System.out.println("Cant get AppUser");
        }

        if (temp.length == 0){
            try{
                temp = editUserRepository.findAllByStudentid(name).get();
            }catch (Exception e){
                System.out.println(name);
                System.out.println("Cant get iduser : " + e);
            }
        }

        PublicUserDataItem publicUserDataItem[] = new PublicUserDataItem[temp.length];

        for (int i = 0;i < temp.length;i++){
            publicUserDataItem[i] = new PublicUserDataItem(
                    temp[i].getIduser(),
                    temp[i].getStudentid(),
                    temp[i].getName(),
                    temp[i].getTag(),
                    temp[i].getSelf_info(),
                    temp[i].getInterest(),
                    temp[i].getImage_path(),
                    temp[i].getSchool(),
                    temp[i].getGender(),
                    temp[i].getRelationship()
            );
        }

        return publicUserDataItem;
    }

    //更新個人資料
    public EditableItem update_user_data(EditableItem appUser) {
        AppUser temp = editUserRepository.findByEmail(appUser.getEmail()).get();
        Boolean checkChange = false;

        if (bCryptPasswordEncoder.matches(appUser.getPassword(),temp.getPassword())){
            checkChange = true;
        } else{
            return null;
        }

        if (checkChange){
            editUserRepository.updateAppUser(
                    appUser.getEmail(),
                    appUser.getName(),
                    appUser.getTag(),
                    appUser.getSelf_info(),
                    appUser.getGender(),
                    appUser.getRelationship(),
                    appUser.getImage_path(),
                    appUser.getInterest()
            );
        }
        return appUser;
    }

    //更新使用者圖片地址
    public String update_user_image_path(UpdateUserImageItem appUser) {
        AppUser temp = editUserRepository.findByEmail(appUser.getEmail()).get();
        Boolean checkChange = false;

        if (bCryptPasswordEncoder.matches(appUser.getPassword(),temp.getPassword())){
            checkChange = true;
        }

        if (checkChange){
            editUserRepository.updateAppUserImage(appUser.getEmail(),appUser.getImage_path());
            return "帳號驗證成功";
        }else return "驗證帳號失敗";
    }
}