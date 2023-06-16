# ntus-ap

# Devlope flow
 ## 前置作業:
  ###  JAVA部分:
  1. 安裝JDK (at least ver.8) 還有設定path
  2. 安裝 IDE
  3. 安裝 Maven
   ###  資料庫:
  1. 安裝 Maria DB : https://officeguide.cc/windows-install-mariadb-database-server-tutorial/
  2. 安裝 MySQL workbench 8.0CE (不可升級8.0以上 MariaDB無法連線) : https://www.it145.com/9/80745.html /記得port要一樣
   ###  Git:
  - 安裝 Github Desktop
   ###  測試:
  - 安裝 Postman 並導入 ntust_ap.postman_collection.json 檔測試
  ## 安裝本程式
  ### 1. 建立資料庫:
  ####    1.使用Maria DB client 建立資料庫 [database名稱 設定ntustap 帳號及密碼皆設定root]
  ####    2.使用mySQL workbench 連線至本地的Maria DB
  #####      A.在首頁點擊+號 建立連線
  #####      B.填入參數 
  #####        connect name -> MariaDB
  #####         connection method -> TCP/IP
  #####         host name -> 127.0.0.1
  #####         port -> 3307 (避免與先前資料庫的port 3306碰撞)
  #####         username -> root
  #####         password -> root
  #####      C.填入SSL參數
  #####         Use SSL -> No
  #####      若再存入資料時發生「Incorrect string value: '\xE7\xA8\x8B\xE5\xBA\x8F...' 」錯誤，請使用以下指令更改資料庫編碼方式。
  #####      ALTER DATABASE 你的資料庫名 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; 
  #####      //來源:https://ubiq.co/database-blog/how-to-change-character-set-from-latin1-to-utf8-in-mysql/
  ###  2. 修改application.yml (如果參數不同 例如我的database名稱是activities)
  ####    如果database 名稱不同 或是帳號密碼不同 請修改
  #####    application.yml底下的
  #####      spring:
  #####        datasource:
  #####          password:密碼
  #####          url:jdbc:mariadb://localhost:3307/database名稱
  #####          username:帳號
  ###  3. 修改 user_friend / FriendService 下第 22-25 行的參數指向本機資料庫
  ###  4. 點選build
