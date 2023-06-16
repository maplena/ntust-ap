package com.example.ntust_ap_server.activity;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ntust_ap_server.activity.Item.*;
import com.example.ntust_ap_server.activity.Repository.*;
import com.example.ntust_ap_server.activity.Request.*;
import com.example.ntust_ap_server.activity.Show.ActivityItemShow;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@AllArgsConstructor
public class ActivityService {
    //
    private final String RUNING = "running";
    private final String END = "end";
    private final static String UPLOADED_FOLDER = "images/activityphoto";
    private final ActivityRepository activityRepository;
    private final CurrentActivityParticipentRepository currentactivityParticipentRepository;
    private final PastActivityParticipentRepository pastActivityParticipentRepository;
    private final ActivityRatingRepository activityRatingRepository;
    private final ReasonRepository_Cancel reasonRepository_Cancel;
    private final ReasonRepositoryKick reasonRepository_Kick;

    /* 當前活動------------------------------------------------------------------- */

    /**
     * 新增活動
     * 
     * @param request
     * @return
     */
    public String addActivity(ActivityRequest request, MultipartFile img) {
        try {
            // 先建立活動物件
            ActivityItem activityItem = new ActivityItem(request);
            String image_path = "";
            // 儲存圖片 取得圖片路徑後 放到活動物件內
            if (img != null) {
                activityRepository.save(activityItem);// 寫入活動資料庫
                image_path = saveActivityPhoto(img, activityItem.getId());
                activityItem.setImage_path(image_path);
            } else {
                activityItem.setImage_path(image_path);
            }
            activityRepository.saveAndFlush(activityItem);
            currentactivityParticipentRepository.save(
                    new CurrentActivityParticipentItem(activityItem.getId(), request));
            // 將參加者寫入參與資料庫
            return "成功";
        } catch (Exception exception) {
            exception.getStackTrace();
            return "失敗";
        }

        // 回傳該活動的主辦人ID
    }

    /**
     * 取得該活動
     * 
     * @param activityId 活動ID
     * @return
     */
    public Optional<ActivityItem> getActivity(String activityId) {
        return activityRepository.findById(Long.parseLong(activityId));
    }

    /**
     * 更新活動
     * 
     * @param request requierd 活動資訊 須包含 requestUserId 及活動Id
     * @param img     optional 活動圖片 不傳則不更動
     * @return 成功時 回傳成功 失敗時 回傳失敗
     */
    public String updateActivity(ActivityRequest request, MultipartFile img) {

        try {
            if (request.getHostID().equals(request.getRequestUserId())) {
                ActivityItem activityItem = new ActivityItem(request);
                String imagePath = "";
                if (img != null) {
                    imagePath = saveActivityPhoto(img, request.getId());
                    activityItem.setImage_path(imagePath);
                } else {
                    activityItem.setImage_path(imagePath);
                }
                activityRepository.saveAndFlush(activityItem);

                return "成功";
            } else
                return "失敗";

        } catch (Exception e) {
            e.printStackTrace();
            return "失敗";
        }
        // 如果userId 等於 活動舉辦者ID

    }

    /**
     * 
     * @param user_id 使用者ID
     * @param act_id  活動ID (string)
     * @return 成功時 回傳成功 失敗時 回傳失敗
     */
    public String stopActivity(String user_id, Long act_id, String reason) {

        // 先確認該user是否為host
        if (checkActivityHost(user_id, act_id)) {
            try {
                /** 將所有參與人員資料 移動至 參與歷史資料庫 */
                LinkedList<CurrentActivityParticipentItem> list = currentactivityParticipentRepository
                        .getAlluserByActivityId(act_id);
                for (CurrentActivityParticipentItem c : list) {
                    PastActivityParticipentItem item = new PastActivityParticipentItem(c);
                    pastActivityParticipentRepository.save(item);
                }
                pastActivityParticipentRepository.flush();

                ActivityItem activityItem = activityRepository.getById(act_id);
                activityItem.setStatus("end");
                activityRepository.save(activityItem);
                activityRepository.flush();
                currentactivityParticipentRepository.deleteAllInBatch(list);
                currentactivityParticipentRepository.flush();// 立即更新

                reasonRepository_Cancel.save(new ReasonActivityCancelItem(act_id, user_id, reason));

                return "成功";

            } catch (Exception e) {
                e.printStackTrace();
                return "失敗，發生錯誤。";
            }

        } else {
            return "失敗";
        }


        // // 是promoter
        // // 執行下架活動
        // // 將活動屬性 "狀態" 改為stop
        // // 回傳完成
        // // 不是promoter
        // // 回傳失敗

        // 轉移參加資料 從current到past資料庫

        // TODO通知所有user該活動被下架

    }

    /**
     * 增加活動人員
     * 
     * @param request
     * @return
     */
    public String addActivityParticipent(ParticipentRequest request) {
        // 確認活動運作中
        if (checkActivityStatus(request.getActivityId(), RUNING)) {
            // 確認是否符合條件
            if (canJoinActivity(request.getActivityId(), request.getSchool(),
                    request.getGrade(), request.getGender())) {
                CurrentActivityParticipentItem a = currentactivityParticipentRepository.findByActivityIdandUserId(
                        request.getActivityId(), request.getUserId());
                if (a == null) {
                    CurrentActivityParticipentItem currentActivityParticipentItem = new CurrentActivityParticipentItem(
                            request);
                    currentactivityParticipentRepository.save(currentActivityParticipentItem);
                    return "成功";
                } else {
                    return "失敗，已參加活動";
                }
            } else
                return "失敗，不符合條件";
        } else {
            return "失敗，該活動已結束。";
        }
    }

    /**
     * 移除活動人員
     * 
     * @param participentRequest
     * @return
     */
    public String removeActivityParticipent(ParticipentRequest participentRequest) {
        if (checkActivityStatus(participentRequest.getActivityId(), RUNING)) {
            try {
                CurrentActivityParticipentItem a = currentactivityParticipentRepository.findByActivityIdandUserId(
                        participentRequest.getActivityId(), participentRequest.getUserId());
                currentactivityParticipentRepository.delete(a);

                return "成功";
            } catch (Exception exception) {
                exception.getStackTrace();
                return "失敗，發生錯誤";
            }
        } else {
            return "失敗，該活動已結束";
        }

    }

    /**
     * 剔除活動人員
     * 
     * @param controlActivityRequest 含活動ID 請求者ID 對象ID
     * @return 成功 or 失敗
     */
    public String kickActivityParticipent(ControlActivityRequest controlActivityRequest) {
        // 先確認是否活動還在執行
        if (checkActivityStatus(controlActivityRequest.getActivityId(), RUNING)) {
            // 再確認 請求者是否舉辦方
            if (checkActivityHost(controlActivityRequest.getRequestUserId(), controlActivityRequest.getActivityId())) {
                // 符合資格 嘗試剔除該活動的指定人員
                try {
                    CurrentActivityParticipentItem a = currentactivityParticipentRepository.findByActivityIdandUserId(
                            controlActivityRequest.getActivityId(), controlActivityRequest.getUserId());
                    currentactivityParticipentRepository.delete(a);

                    reasonRepository_Kick.save(
                            new ReasonKick(
                                    controlActivityRequest.getActivityId(),
                                    controlActivityRequest.getRequestUserId(),
                                    controlActivityRequest.getUserId(),
                                    controlActivityRequest.getReason()));

                    return "成功";
                } catch (Exception exception) {
                    exception.getStackTrace();
                    return "失敗，發生錯誤";
                }
            } else
                return "失敗，您沒有權限";

        } else {
            return "失敗，該活動已結束";
        }

    }

    /**
     * 取得當前活動參與者列表
     * 
     * @param activityid
     * @param page
     * @return
     */
    public List<CurrentActivityParticipentItem> getCurrentActivityParticipentListByActivityId(String activityid,
            String page) {
        Long activityidlong = Long.parseLong(activityid);// 將ID轉換為Long
        int pagenumber = Integer.parseInt(page);
        int pagesize = 20;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        List<CurrentActivityParticipentItem> participentList = currentactivityParticipentRepository
                .findByActivityId(activityidlong, pagereq).getContent();
        for (CurrentActivityParticipentItem user : participentList) {
            user.setPhone(null);
        }
        return participentList;
    }

    /**
     * 取得已報名的活動列表
     * 
     * @param userId 使用者ID
     * @param page   頁數 一頁20個
     *               // * @param status 狀態 running=尚在開放報名的活動 lock=報名截止的活動
     *               end=已經結束的活動
     * @return
     */
    public List<ActivityItem> getMyCurrentActivities(String userId, String page) {
        List<Long> list = currentactivityParticipentRepository.findByUserId(userId);
        List<ActivityItem> activitylist = new LinkedList<>();
        activitylist.addAll(activityRepository.findAllById(list));

        return activitylist;

    }

    public int getCurrentActivityParticipentNumberByActivityId(Long activityid) {
        return currentactivityParticipentRepository.getNumberByActivityId(activityid);
    }

    @Resource
    private ImageRequestHandler imageRequestHandler;

    public void getActivityPhoto(String activityId, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        Path path = Paths.get(UPLOADED_FOLDER, activityId + ".jpg");
        File file = new File(path.toAbsolutePath().toString());
        httpServletRequest.setAttribute(ImageRequestHandler.ATTRIBUTE_FILE, file);
        imageRequestHandler.handleRequest(httpServletRequest, httpServletResponse);
    }
    /* 取得活動列表------------------------------------------------------------------- */

    /**
     * 取得活動列表 已限制內容
     * 
     * @param activityRequest
     * @return
     */
    public List<ActivityItemShow> getActivitiylist(GetActivityRequest activityRequest) {
        /** 取得活動列表(by type) */
        int pagenumber = activityRequest.getPage();
        int pagesize = 10;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);

        List<ActivityItem> activityItems = activityRepository.findBylimit(activityRequest.getSchool_limit(),
                activityRequest.getGrade(), activityRequest.getGender(), RUNING, pagereq).getContent();

        List<ActivityItemShow> showlist = new ArrayList<>();
        for (ActivityItem item : activityItems) {
            ActivityItemShow showitem = new ActivityItemShow(item,
                    getCurrentActivityParticipentNumberByActivityId(item.getId()));
            showlist.add(showitem);
        }
        return showlist;
    }

    /*
     * 報名的活動只有 開放與鎖定
     * 但是已報名的活動中 會有尚在開放的活動 以及報名截止 但尚未開始的活動
     */
    /**
     * 取得活動列表(by type)
     * 
     * @param type   種類 如自辦 社團 或校辦
     * @param page   頁數
     * @param status 狀態
     *               running表示開放中
     *               lock表示截止報名
     *               end表示結束
     * @return
     *         陣列 含活動物件
     */
    public List<ActivityItem> getActivitiylistbytype(String type, String page, String status) {
        /** 取得活動列表(by type) */
        int pagenumber = Integer.parseInt(page);
        int pagesize = 10;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        return activityRepository.findBytype(type, status, pagereq).getContent();
    }

    /**
     * 取得活動列表(by tag)
     * 
     * @param tag
     * @param page
     * @param status
     * @return
     */
    public List<ActivityItem> getActivitiylistbytag(String tag, String page, String status) {
        int pagenumber = Integer.parseInt(page);
        int pagesize = 10;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        String search = "%" + tag + "%";
        return activityRepository.findBytag(search, status, pagereq).getContent();
    }

    /**
     * 取得活動列表(by 主辦者)
     * 
     * @param hostId
     * @param page
     * @param status
     * @return
     */
    public List<ActivityItemShow> getActivitiylistbyhost(String hostId, String page, String status) {
        int pagenumber = Integer.parseInt(page);
        int pagesize = 10;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        List<ActivityItem> activityItems = activityRepository.findByHost(hostId, status, pagereq).getContent();

        List<ActivityItemShow> showlist = new ArrayList<>();
        for (ActivityItem item : activityItems) {
            ActivityItemShow showitem = new ActivityItemShow(item,
                    getCurrentActivityParticipentNumberByActivityId(item.getId()));
            showlist.add(showitem);
        }
        return showlist;
    }

    /* 歷史活動------------------------------------------------------------------- */

    /**
     * 取得歷史活動參與者列表
     * 
     * @param activityid
     * @param page
     * @return
     */
    public List<PastActivityParticipentItem> getPastActivityParticipentListByActivityId(String activityid,
            String page) {
        Long activityidlong = Long.parseLong(activityid);// 將ID轉換為Long
        int pagenumber = Integer.parseInt(page);
        int pagesize = 20;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        return pastActivityParticipentRepository.findByActivityId(activityidlong, pagereq).getContent();

    }

    /**
     * 取得活動歷史紀錄
     * 
     * @param userId
     * @param page
     * @return
     */
    public List<ActivityItem> getMyPastActivities(String userId, String page) {

        int pagenumber = Integer.parseInt(page);
        int pagesize = 20;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);

        List<Long> list = pastActivityParticipentRepository.getActivityIdByUserId(userId, pagereq);
        List<ActivityItem> activitylist = new LinkedList<>();
        activitylist.addAll(activityRepository.findAllById(list));
        return activitylist;

    }

    /* 活動評價------------------------------------------------------------------- */

    /**
     * 評價活動
     * 
     * @param activityRatingItem
     * @return 成功or 失敗
     */
    public String ratingActivity(ActivityRatingItem activityRatingItem) {
        Long activityId = activityRatingItem.getActivityId();
        // 如果活動已經結束 才可以評價
        if (checkActivityStatus(activityId, END)) {
            // 已經評價過 但是要修改
            if (activityRatingItem.getId() != null) {
                activityRatingRepository.saveAndFlush(activityRatingItem);
            } else {
                // 先看看是不是已經有評價
                Long id = checkHaveRating(activityRatingItem);
                if (id == -1L) {
                    activityRatingRepository.saveAndFlush(activityRatingItem);
                } else {
                    return "失敗，已評價過。";
                }
            }
            // 尚未評價過 儲存

            return "成功";
        } else {
            return "失敗，活動尚未結束";
        }
    }

    public List<ActivityRatingItem> getActivityRatingList(Long activityId, String page) {
        if (checkActivityStatus(activityId, END)) {
            int pagenumber = Integer.parseInt(page);
            int pagesize = 10;
            Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
            return activityRatingRepository.getListByActivityId(activityId, pagereq).getContent();
        } else
            return Collections.emptyList();

    };

    /**
     * 取得活動平均評價
     * 
     * @param activityId
     * @return Double 平均評價 至小數點第一位
     */
    public Double getActivityRatingAVR(String activityId) {

        if (checkActivityStatus(Long.parseLong(activityId), END)) {
            return Math.round(activityRatingRepository.getActivityAvgRating(Long.parseLong(activityId)) * 10.0) / 10.0;
        } else
            return 0.0;

    }

    /* 確認用方法------------------------------------------------------------------- */

    /**
     * 確認活動狀態
     * 
     * @param activityId
     * @return 相符時 回傳true 否則false
     */
    public boolean checkActivityStatus(Long activityId, String status) {
        return activityRepository.getStatusByid(activityId).equals(status);
    }

    /**
     * 確認活動參加資格
     * 
     * @param activityId 活動ID
     * @param school     使用者校別
     * @param grade      使用者年級
     * @param gender     使用者性別
     * @return true or false
     */
    public boolean canJoinActivity(Long activityId, int school, int grade, int gender) {
        ActivityItem activityItem = activityRepository.getById(activityId);
        if (activityItem.getSchool_limit() == school || activityItem.getSchool_limit() == 0) {
            if (activityItem.getGrade() == grade || activityItem.getGrade() == 0) {
                if (activityItem.getGender() == gender || activityItem.getGender() == 0) {
                    return true;
                } else
                    return false;
            } else
                return false;
        } else
            return false;
    }

    /**
     * 確認是否為主辦者
     * 
     * @param requestUserId 請求者ID
     * @param activityId    活動ID
     * @return true or false
     */
    public boolean checkActivityHost(String requestUserId, Long activityId) {
        return activityRepository.getHostByid(activityId).equals(requestUserId);
    }

    public Long checkHaveRating(ActivityRatingItem activityRatingItem) {
        Long id = activityRatingRepository.findByUserId(activityRatingItem.getActivityId(),
                activityRatingItem.getUserId());
        if (id == null) {
            return -1L;
        } else
            return id;
    }

    /* 上傳* */

    public String saveActivityPhoto(MultipartFile img, Long id) {
        try {
            Path path = Paths.get(UPLOADED_FOLDER, Long.toString(id) + getExtension(img));
            img.transferTo(new File(path.toAbsolutePath().toString()));
            return path.toAbsolutePath().toString();
        } catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }

    }

    /**
     * 回傳副檔名 含"."
     * 
     * @param file
     * @return
     */
    private String getExtension(MultipartFile file) throws Exception{
        String originalFilename = file.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf('.'));
    }
}
