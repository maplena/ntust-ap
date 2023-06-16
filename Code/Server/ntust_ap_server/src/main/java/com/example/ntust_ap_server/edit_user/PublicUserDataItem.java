package com.example.ntust_ap_server.edit_user;

// 2021.12.11 Boyan Start End

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
//公開資料範圍物件
public class PublicUserDataItem {
    private final Long iduser;
    private final String student_id, name, tag, self_info, intrest, image_path, school;// 主辦人 校別 地點 標籤 詳細資料
    private final Boolean gender;
    private final int relationship;

}
