package com.example.ntust_ap_server.activity;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ntust_ap_server.activity.Item.*;
import com.example.ntust_ap_server.activity.Request.*;
import com.example.ntust_ap_server.activity.Show.ActivityItemShow;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/*活動物件 的控制台 */

@RestController
@RequestMapping(path = "api/v1/activity", produces = MediaType.APPLICATION_JSON_VALUE) // 設定 URL path
@AllArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    // mapping的順序 是先走@RequestMapping的path進來 再找method的mapping的
    // postmapping指的是 先判斷是不是post 是post再來找path 執行對應method
    // getmapping 則是先判斷是不是get 是get再來找path 執行對應method

    /*
     * For host
     * add
     * 建立活動
     * edit(update)
     * 更新活動 含更新狀態(開放報名 開放中 結束報名 結束/下架)
     * getmyactivity
     * 取得自己舉辦的活動
     * 
     * For participant
     * join
     * 加入/報名活動
     * leave
     * 離開活動
     * rate
     * 評分活動(僅限活動結束)
     * getactivities
     * 取得活動列表
     * 
     */

    /* 當前活動------------------------------------------------------------------- */

    /**
     * 增加活動物件
     * 用法 呼叫api/v1/Activity/add
     * 並傳送json的activityitem物件 以及活動圖片
     * 
//     * @param activityRequest 活動物件 以JSON包覆
     * @return 當成功時 回傳成功
     *         失敗時 回傳失敗
     */
    @PostMapping(value = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public String addActivity(@RequestPart(name = "img",required = false) MultipartFile img,
            @RequestPart(name = "request") ActivityRequest request) {
        return activityService.addActivity(request, img);
    }

    /**
     * 取得活動物件
     * 
     * @param activityId 活動ID
     * @return 物件的JSON
     */
    @GetMapping("/get/{id}")
    public Optional<ActivityItem> getActivityItem(@PathVariable("id") String activityId) {
        return activityService.getActivity(activityId);
    }

    /**
     * 更新活動
     * 
     * @apiNote
     *          用法 呼叫api/v1/Activity/update/{user_id}
     * 
//     * @param activityItem 活動物件
//     * @param userId       使用者ID
     * @return
     */
    @PostMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public String updateActivity(@RequestPart(name = "img", required = false) MultipartFile img,
            @RequestPart(name = "request") ActivityRequest request) {
        return activityService.updateActivity(request, img);
    }

    /**
     * 下架活動
     * 
     * @apiNote 用法 呼叫api/v1/Activity/stop
     * @param controlActivityRequest
     * @return
     */
    @PostMapping("/stop")
    public String stopActivity(@RequestBody ControlActivityRequest controlActivityRequest) {
        return activityService.stopActivity(
                controlActivityRequest.getRequestUserId(),
                controlActivityRequest.getActivityId(),
                controlActivityRequest.getReason());
    }

    /**
     * 參加活動
     * 
//     * @param activityParticipentItem 參與物件
     * @return 成功 or 失敗
     */
    @PostMapping("/joinactivity")
    public String joinActivity(@RequestBody ParticipentRequest participentRequest) {

        return activityService.addActivityParticipent(participentRequest);
    }

    /**
     * 退出活動
     * 
//     * @param activityParticipentItem
     * @return 成功 or 失敗
     */
    @PostMapping("/leaveactivity")
    public String leaveActivity(@RequestBody ParticipentRequest participentRequest) {
        return activityService.removeActivityParticipent(participentRequest);
    }

    /**
     * 退出活動
     * 
//     * @param participentRequest
     * @return 成功 or 失敗
     */
    @PostMapping("/kickactivity")
    public String kickActivity(@RequestBody ControlActivityRequest controlActivityRequest) {
        return activityService.kickActivityParticipent(controlActivityRequest);
    }

    /**
     * 取得當前活動參加人員
     * 
     * @apiNote
     *          用法 api/v1/Activity/getparticipent/current/123456/0
     *          表示 活動ID = 123456 頁數=0
     * @param activityid 活動ID
     * @param page       頁數
     * @return List of users
     */
    @GetMapping("/getparticipent/current/{activityid}/{page}")
    public List<CurrentActivityParticipentItem> getCurrentActivitiyParticipentByActivityId(
            @PathVariable("activityid") String activityid,
            @PathVariable("page") String page) {
        return activityService.getCurrentActivityParticipentListByActivityId(activityid, page);
    }

    /**
     * 取得我的當前參加的活動列表
     * 
     * @param userId 使用者ID
     * @param page   頁數
     * @return
     */
    @GetMapping("/getmyactivities/current/{userId}/{page}")
    public List<ActivityItem> getmyCurrentActivitiyByuserId(
            @PathVariable("userId") String userId,
            @PathVariable("page") String page) {
        return activityService.getMyCurrentActivities(userId, page);
    }

    @GetMapping("/getPhoto/{id}")
    public void getActivityPhoto(@PathVariable("id") String activityId,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        try {
            activityService.getActivityPhoto(activityId,httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }


    /* 取得活動列表------------------------------------------------------------------- */

    /**
     * 取得活動 by limit
     * 
     * @param getactivityRequest GetActivityRequest物件 內含學校 年級 性別限制 以及頁數
     * @return List of 活動物件
     */
    @PostMapping("/getbylimit")
    public List<ActivityItemShow> getActivityByLimit(@RequestBody GetActivityRequest getactivityRequest) {
        return activityService.getActivitiylist(getactivityRequest);
    }

    /**
     * 取得活動列表 (依種類)
     * 
     * @apiNote
     *          用法 api/v1/Activity/get/type=校辦&status=running/0
     *          表示搜尋活動 標籤=動漫 狀態=正在執行 頁數=0(第一頁)
     * 
     * @param type   活動種類
     * @param status 活動當前狀態
     * @param page   頁數(從0開始標記)，每頁上限10個item
     * @return List of 活動物件
     */
    @GetMapping("/get/type={type}&status={status}/{page}")
    public List<ActivityItem> getActivitiybytype(
            @PathVariable("type") String type,
            @PathVariable("status") String status,
            @PathVariable("page") String page) {
        return activityService.getActivitiylistbytype(type, page, status);
    }

    /**
     * 取得活動列表 (依照標籤)
     * 
     * @apiNote
     *          用法 api/v1/Activity/get/tag=anime&status=running/0
     *          表示搜尋活動 標籤=動漫 狀態=正在執行 頁數=0(第一頁)
     * @param tag    活動標籤
     * @param status 當前狀態
     * @param page   頁數
     * @return List of 活動物件
     */
    @GetMapping("/get/tag={tag}&status={status}/{page}")
    public List<ActivityItem> getActivitiybytag(
            @PathVariable("tag") String tag,
            @PathVariable("status") String status,
            @PathVariable("page") String page) {
        return activityService.getActivitiylistbytag(tag, page, status);
    }

    /**
     * 取得活動列表 (依照主辦者)
     * 
     * @apiNote
     *          用法 api/v1/Activity/get/promoter=b10809001&status=running/0
     *          表示搜尋活動 主辦者ID=b10809001 狀態=正在執行 頁數=0(第一頁)
     * @param hostId 活動主辦者ID
     * @param status 當前狀態
     * @param page   頁數
     * @return List of 活動物件
     */
    @GetMapping("/get/host={hostId}&status={status}/{page}")
    public List<ActivityItemShow> getActivitiybypromoter(
            @PathVariable("hostId") String hostId,
            @PathVariable("status") String status,
            @PathVariable("page") String page) {
        return activityService.getActivitiylistbyhost(hostId, page, status);
    }

    /* 歷史活動------------------------------------------------------------------- */
    /**
     * 取得歷史活動參加人員
     * 
     * @param activityid
     * @param page
     * @return
     */
    @GetMapping("/getparticipent/past/{activityid}/{page}")
    public List<PastActivityParticipentItem> getPastActivitiyParticipentByActivityId(
            @PathVariable("activityid") String activityid,
            @PathVariable("page") String page) {
        return activityService.getPastActivityParticipentListByActivityId(activityid, page);
    }

    /**
     * 取得我的歷史活動列表
     * 
     * @apiNote api/v1/activity/getmyactivities/past/{userId}/{page}
     * @param userId 使用者ID
     * @param page   頁數
     * @return list of activityID in json format
     */
    @GetMapping("/getmyactivities/past/{userId}/{page}")
    public List<ActivityItem> getmyPastActivitiyByuserId(
            @PathVariable("userId") String userId,
            @PathVariable("page") String page) {
        return activityService.getMyPastActivities(userId, page);
    }

    /* 活動評價------------------------------------------------------------------- */

    /**
     * 評價活動
     * 
     * @param activityRatingItem 如果要更新評價 JSON中請記得加入該評價的id
     * @return 成功or 失敗
     */
    @PostMapping("/rating")
    public String ratingActivity(@RequestBody ActivityRatingItem activityRatingItem) {
        return activityService.ratingActivity(activityRatingItem);
    }

    @GetMapping("/getratinglist/{activityid}/{page}")
    public List<ActivityRatingItem> getActivityRatingList(
            @PathVariable("activityid") String activityid,
            @PathVariable("page") String page) {
        return activityService.getActivityRatingList(Long.parseLong(activityid), page);

    }

    /**
     * 取得平均評價
     * 
     * @param activityId
     * @return
     */
    @GetMapping("/getrating/{activityId}")
    public Double getActivityRating(@PathVariable("activityId") String activityId) {
        return activityService.getActivityRatingAVR(activityId);
    }

}
