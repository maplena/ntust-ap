package com.example.ntust_ap_server.survey;

import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ntust_ap_server.survey.Item.CurrentSurveyParticipentItem;
import com.example.ntust_ap_server.survey.Item.PastSurveyParticipentItem;
import com.example.ntust_ap_server.survey.Item.SurveyItem;
import com.example.ntust_ap_server.survey.Request.ControlSurveyRequest;
import com.example.ntust_ap_server.survey.Request.GetSurveyRequest;
import com.example.ntust_ap_server.survey.Request.ParticipentRequest;
import com.example.ntust_ap_server.survey.Request.SurveyRequest;

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
@RequestMapping(path = "api/v1/survey", produces = MediaType.APPLICATION_JSON_VALUE) // 設定 URL path
@AllArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    // mapping的順序 是先走@RequestMapping的path進來 再找method的mapping的
    // postmapping指的是 先判斷是不是post 是post再來找path 執行對應method
    // getmapping 則是先判斷是不是get 是get再來找path 執行對應method

    @PostMapping(value = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public String addSurvey(@RequestPart(name = "img",required = false) MultipartFile img,
            @RequestPart(name = "request") SurveyRequest surveyRequest) {
        return surveyService.addSurvey(surveyRequest, img);
    }

    /* 此方法為取得活動物件 回傳該物件的JSON */
    @GetMapping("/get/{id}")
    public Optional<SurveyItem> getSurveyItem(@PathVariable("id") String id) {
        return surveyService.getSurvey(id);
    }

    // 用法 呼叫api/v1/survey/update
    @PostMapping("/update")
    public String updateSurvey(@RequestPart(name = "img", required = false) MultipartFile img,
            @RequestPart(name = "request") SurveyRequest surveyRequest) {
        return surveyService.updateSurvey(surveyRequest, img);
    }

    /**
     * 下架活動
     * 
     * @apiNote 用法 呼叫api/v1/survey/stop
     * @param controlActivityRequest
     * @return 成功 or 失敗
     */
    @PostMapping("/stop")
    public String stopSurvey(@RequestBody ControlSurveyRequest controlActivityRequest) {
        return surveyService.stopSurvey(
                controlActivityRequest.getRequestUserId(),
                controlActivityRequest.getSurveyId(),
                controlActivityRequest.getReason());
    }

    /**
     * 參加問卷
     * 
     * @param request
     * @return
     */
    @PostMapping("/joinsurvey")
    public String joinSurvey(@RequestBody ParticipentRequest request) {
        return surveyService.addSurveyParticipent(request);
    }

    /**
     * 取得當前填問卷的名單
     * 
     * @param surveyId
     * @param page
     * @return
     */
    @GetMapping("/getparticipent/current/{surveyId}/{page}")
    public List<CurrentSurveyParticipentItem> getSurveyParticipentBySurveyId(
            @PathVariable("surveyId") String surveyId,
            @PathVariable("page") String page) {
        return surveyService.getCurrentSurveyParticipentListBySurveyId(surveyId, page);
    }

    /**
     * 取得我的當前參加的活動列表
     * 
     * @param userId 使用者ID
     * @param page   頁數
     * @return
     */
    @GetMapping("/getmysurveys/current/{userId}/{page}")
    public List<CurrentSurveyParticipentItem> getmyCurrentSurveyByuserId(
            @PathVariable("userId") String userId,
            @PathVariable("page") String page) {
        return surveyService.getMyCurrentSurveys(userId, page);
    }


    @GetMapping("/getPhoto/{id}")
    public void getSurveyPhoto(@PathVariable("id") String surveyId, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        try {
            surveyService.getSurveyPhoto(surveyId, httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* 取得問卷列表------------------------------------------------------------------- */

    

    /**
     * 取得活動 by limit
     * 
     * @param getactivityRequest GetActivityRequest物件 內含學校 年級 性別限制 以及頁數
     * @return List of 活動物件
     */
    @PostMapping("/getbylimit")
    public List<SurveyItem> getSurveyByLimit(@RequestBody GetSurveyRequest request) {
        return surveyService.getSurveylist(request);
    }

    /**
     * 此方法為取得依照種類取得活動列表
     * 
     * @param tag    活動標籤
     * @param status 當前狀態
     * @param page   頁數(從0開始標記) 每頁上限10個item
     * @return List of SurveyItem
     * @apiNote api/v1/Activity/get/tag=anime&status=running/0 表示搜尋活動 標籤=動漫 狀態=正在執行
     *          頁數=0(第一頁)
     */
    @GetMapping("/get/tag={tag}&status={status}/{page}")
    public List<SurveyItem> getSurveyByTag(
            @PathVariable("tag") String tag,
            @PathVariable("status") String status,
            @PathVariable("page") String page) {
        return surveyService.getSurveylistbytag(tag, page, status);
    }

    /**
     * 取得問卷 by 發起者 如果要找自己發起的問卷 hostId填入自己Id即可
     * 
     * @param hostId
     * @param status 該問卷狀態 可為running or end
     * @param page
     * @return
     */
    @GetMapping("/get/host={hostId}&status={status}/{page}")
    public List<SurveyItem> getSurveybyhost(
            @PathVariable("hostId") String hostId,
            @PathVariable("status") String status,
            @PathVariable("page") String page) {
        return surveyService.getSurveylistbypromoter(hostId, page, status);
    }

    /**
     * 取得歷史活動參加人員
     * 
     * @param surveyId
     * @param page
     * @return
     */
    @GetMapping("/getparticipent/past/{surveyId}/{page}")
    public List<PastSurveyParticipentItem> getPastSurveyParticipentBySurveyId(
            @PathVariable("surveyId") String surveyId,
            @PathVariable("page") String page) {
        return surveyService.getPastSurveyParticipentListBySurveyId(surveyId, page);
    }

    /**
     * 取得我的歷史活動列表
     * 
     * @apiNote api/v1/activity/getmyactivities/past/{userId}/{page}
     * @param userId 使用者ID
     * @param page   頁數
     * @return list of activityID in json format
     */
    @GetMapping("/getmysurveys/past/{userId}/{page}")
    public List<PastSurveyParticipentItem> getmyPastSurveyByuserId(
            @PathVariable("userId") String userId,
            @PathVariable("page") String page) {
        return surveyService.getMyPastSurveys(userId, page);
    }
    

}
