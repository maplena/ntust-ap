package com.example.ntust_ap_server.edit_user;
public class User{

    /*全域變數*/
    private String ID;



    /*建構式 */
    public User(String user_id) {
        this.ID = user_id;
    }

    


    /*方法 */

    /*個人區域=======================================================================================*/

    //取得個人資料(開放)
    public void get_user_public() {
        
    }

    //取得個人資料(限本人)
    public void get_user_private() {
        
    }

    //編輯個人暱稱
    public boolean Set_Nickname() {
        return false;
    }

    //編輯自我介紹
    public boolean set_introduction() {
        return false;
    }

    //編輯個人興趣
    public boolean set_hobbies() {
        return false;
    }

    //編輯個人感情狀態
    public boolean set_relation() {
        return false;
    }
    
    //編輯個人頭貼
    public boolean set_photo() {
        return false;
    }

    //編輯密碼
    public boolean set_password() {
        try {
            

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*朋友區=======================================================================================*/

    //加朋友
    public boolean add_friend() {
        return true;
    }

    //刪除朋友
    public boolean remove_friend() {
        return true;
    }

    //加入自己的 待確認朋友列表 (僅限A加B時 將A的ID加入到B的待確認列表內)
    public boolean add_into_accept_list() {
        return true;
    }

    //加入自己的 已送出交友邀請列表(僅限A加B時 將B的ID加入到A的已送出邀請列表內)
    public boolean add_into_wait_accept_list() {
        return true;
    }

    //取得當前朋友列表
    public void get_friend_list(){
        
    }
    
    /*活動區=======================================================================================*/

    //取得當前已報名活動
    public void get_activity() {
        
    }

    //取得活動參加紀錄
    public void get_activity_history() {
        
    }

    //參加/報名活動
    public void join_activity() {
        
    }

    //取消活動
    public void left_activity() {
        
    }

    



    

}