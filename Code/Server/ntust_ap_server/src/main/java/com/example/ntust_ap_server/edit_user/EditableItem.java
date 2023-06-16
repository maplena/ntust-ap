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
//RequestBody 用物件
public class EditableItem {
    private String name,email,password,tag,self_info,image_path,interest;
    private Boolean gender;
    private int relationship;
}
