package com.example.ntust_ap_server.edit_user;

// 2021.12.11 Boyan Start End

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
//使用者圖片位置設定物件
public class UpdateUserImageItem {
    private String email,password,image_path;
}
