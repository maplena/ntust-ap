package com.example.ntust_ap_server.survey;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ntust_ap_server.survey.Item.SurveyItem;
import com.example.ntust_ap_server.activity.ImageRequestHandler;
import com.example.ntust_ap_server.survey.Item.CurrentSurveyParticipentItem;
import com.example.ntust_ap_server.survey.Item.PastSurveyParticipentItem;
import com.example.ntust_ap_server.survey.Item.ReasonSurveyCancelItem;
import com.example.ntust_ap_server.survey.Repository.CurrentSurveyParticipentRepository;
import com.example.ntust_ap_server.survey.Repository.PastSurveyParticipentRepository;
import com.example.ntust_ap_server.survey.Repository.ReasonRepository_SurveyCancel;
import com.example.ntust_ap_server.survey.Repository.SurveyRepository;
import com.example.ntust_ap_server.survey.Request.GetSurveyRequest;
import com.example.ntust_ap_server.survey.Request.ParticipentRequest;
import com.example.ntust_ap_server.survey.Request.SurveyRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/*問卷的服務 */

@Service
@AllArgsConstructor
public class SurveyService {
    //
    private final SurveyRepository surveyRepository;
    private final CurrentSurveyParticipentRepository currentSurveyParticipentRepository;
    private final PastSurveyParticipentRepository pastSurveyParticipentRepository;
    private final ReasonRepository_SurveyCancel reasonRepository_SurveyCancel;
    private final String RUNING = "running";
    private final static String UPLOADED_FOLDER = "images/surveyphoto";

    /* 當前問卷------------------------------------------------------------------- */

    /**
     * 新增問卷
     * 
     * @param request 要求
     * @param img     圖片
     * @return
     */
    public String addSurvey(SurveyRequest request, MultipartFile img) {
        try {
            SurveyItem surveyItem = new SurveyItem(request);
            String image_path = "";
            if (img != null) {
                surveyRepository.save(surveyItem);
                image_path = saveSurveyPhoto(img, surveyItem.getId());
                surveyItem.setImage_path(image_path);
            } else {
                surveyItem.setImage_path(image_path);
            }
            surveyRepository.saveAndFlush(surveyItem);
            return "成功";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "失敗";
        }

        // 回傳該問卷的主辦人ID
    }

    /**
     * 存取該問卷
     * 
     * @param surveyId 問卷Id
     * @return
     */
    public Optional<SurveyItem> getSurvey(String surveyId) {
        return surveyRepository.findById(Long.parseLong(surveyId));
    }

    /**
     * 更新問卷
     * 
     * @param request
     * @param img
     * @return
     */
    public String updateSurvey(SurveyRequest request, MultipartFile img) {
        if (request.getHostID().equals(request.getRequestUserId())) {
            if (img == null) {
                surveyRepository.saveAndFlush(new SurveyItem(request));
            } else {
                try {
                    String image_path = saveSurveyPhoto(img, request.getId());
                    request.setImage_path(image_path);
                    surveyRepository.saveAndFlush(new SurveyItem(request));
                } catch (Exception e) {
                    return "失敗";
                }
            }

            return "成功";
        } else
            return "失敗";
    }

    /**
     * 
     * @param userId   使用者ID
     * @param surveyId 活動ID (string)
     * @return 成功時 回傳成功 失敗時 回傳失敗
     */
    public String stopSurvey(String userId, Long surveyId, String reason) {

        // 先確認該user是否為host
        if (checkSurveyHost(userId, surveyId)) {
            try {
                /** 將所有參與人員資料 移動至 參與歷史資料庫 */
                LinkedList<CurrentSurveyParticipentItem> list = currentSurveyParticipentRepository
                        .getAlluserBySurveyId(surveyId);
                for (CurrentSurveyParticipentItem c : list) {
                    PastSurveyParticipentItem item = new PastSurveyParticipentItem(c);
                    pastSurveyParticipentRepository.save(item);
                }
                pastSurveyParticipentRepository.flush();

                SurveyItem SurveyItem = surveyRepository.getById(surveyId);
                SurveyItem.setStatus("end");
                surveyRepository.save(SurveyItem);
                surveyRepository.flush();
                currentSurveyParticipentRepository.deleteAllInBatch(list);
                currentSurveyParticipentRepository.flush();// 立即更新

                reasonRepository_SurveyCancel.save(new ReasonSurveyCancelItem(surveyId, userId, reason));

                return "成功";

            } catch (Exception e) {
                e.printStackTrace();
                return "失敗，發生錯誤。";
            }

        } else {
            return "失敗";
        }

    }

    public String addSurveyParticipent(ParticipentRequest request) {
        // 確認活動運作中
        if (checkSurveyStatus(request.getSurveyId(), RUNING)) {
            // 確認是否符合條件
            if (canJoinSurvey(request.getSurveyId(), request.getSchool(),
                    request.getGrade(), request.getGender())) {
                CurrentSurveyParticipentItem a = currentSurveyParticipentRepository.findBySurveyIdandUserId(
                        request.getSurveyId(), request.getUserId());
                if (a == null) {
                    CurrentSurveyParticipentItem currentActivityParticipentItem = new CurrentSurveyParticipentItem(
                            request);
                    currentSurveyParticipentRepository.save(currentActivityParticipentItem);
                    return "成功";
                } else {
                    return "失敗，已參加問卷";
                }
            } else
                return "失敗，不符合條件";
        } else {
            return "失敗，該問卷已結束";
        }

    }

    @Resource
    private ImageRequestHandler imageRequestHandler;

    public void getSurveyPhoto(String surveyId, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        Path path = Paths.get(UPLOADED_FOLDER, surveyId + ".jpg");
        File file = new File(path.toAbsolutePath().toString());
        httpServletRequest.setAttribute(ImageRequestHandler.ATTRIBUTE_FILE, file);
        imageRequestHandler.handleRequest(httpServletRequest, httpServletResponse);
    }

    // 移除問卷人員
    public String removeSurveyParticipent(CurrentSurveyParticipentItem currentSurveyParticipentItem) {
        try {
            CurrentSurveyParticipentItem a = currentSurveyParticipentRepository.findBySurveyIdandUserId(
                    currentSurveyParticipentItem.getSurveyId(), currentSurveyParticipentItem.getUserId());
            currentSurveyParticipentRepository.delete(a);

            return "成功";
        } catch (Exception exception) {
            exception.getStackTrace();
            return "失敗";
        }
    }

    /**
     * 取得當前問卷參與者列表
     * 
     * @param surveyId
     * @param page
     * @return
     */
    public List<CurrentSurveyParticipentItem> getCurrentSurveyParticipentListBySurveyId(String surveyId, String page) {
        Long surveyidlong = Long.parseLong(surveyId);// 將ID轉換為Long
        int pagenumber = Integer.parseInt(page);
        int pagesize = 20;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);

        return currentSurveyParticipentRepository.findBySurveyId(surveyidlong, pagereq).getContent();
    }

    /**
     * 取得已參與的問卷列表
     * 
     * @param userId
     * @param page
     * @return
     */
    public List<CurrentSurveyParticipentItem> getMyCurrentSurveys(String userId, String page) {

        int pagenumber = Integer.parseInt(page);
        int pagesize = 20;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        List<CurrentSurveyParticipentItem> list = currentSurveyParticipentRepository.findByUserId(userId, pagereq)
                .getContent();
        return list;
    }

    /* 取得問卷列表------------------------------------------------------------------- */

    /**
     * 取得問卷列表 已限制內容
     * 
     * @param request
     * @return
     */
    public List<SurveyItem> getSurveylist(GetSurveyRequest request) {
        /** 取得活動列表(by type) */
        int pagenumber = request.getPage();
        int pagesize = 10;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        return surveyRepository.findBylimit(request.getSchool_limit(),
                request.getGrade(), request.getGender(), RUNING, pagereq).getContent();
    }

    // 取得問卷列表(by tag)
    public List<SurveyItem> getSurveylistbytag(String tag, String page, String status) {
        int pagenumber = Integer.parseInt(page);
        int pagesize = 10;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        String search = "%" + tag + "%";
        return surveyRepository.findBytag(search, status, pagereq).getContent();
    }

    // 取得問卷列表(by promoter)
    public List<SurveyItem> getSurveylistbypromoter(String promoter, String page, String status) {
        int pagenumber = Integer.parseInt(page);
        int pagesize = 10;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);

        return surveyRepository.findByHostId(promoter, status, pagereq).getContent();
    }

    /* 歷史問卷------------------------------------------------------------------- */

    /**
     * 取得歷史問卷參與者列表
     * 
     * @param surveyId
     * @param page
     * @return
     */
    public List<PastSurveyParticipentItem> getPastSurveyParticipentListBySurveyId(String surveyId,
            String page) {
        Long surveyidlong = Long.parseLong(surveyId);// 將ID轉換為Long
        int pagenumber = Integer.parseInt(page);
        int pagesize = 20;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        return pastSurveyParticipentRepository.findBySurveyId(surveyidlong, pagereq).getContent();

    }

    /**
     * 取得活動歷史紀錄
     * 
     * @param userId
     * @param page
     * @return
     */
    public List<PastSurveyParticipentItem> getMyPastSurveys(String userId, String page) {

        int pagenumber = Integer.parseInt(page);
        int pagesize = 20;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);

        List<PastSurveyParticipentItem> list = pastSurveyParticipentRepository.findByUserId(userId, pagereq)
                .getContent();
        return list;

    }

    /** 取得問卷數量 */

    /* 確認用方法------------------------------------------------------------------- */

    /**
     * 確認活動狀態
     * 
     * @param surveyId
     * @return 相符時 回傳true 否則false
     */
    public boolean checkSurveyStatus(Long surveyId, String status) {
        return surveyRepository.getStatusByid(surveyId).equals(status);
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
    public boolean canJoinSurvey(Long activityId, int school, int grade, int gender) {
        SurveyItem SurveyItem = surveyRepository.getById(activityId);
        if (SurveyItem.getSchool_limit() == school || SurveyItem.getSchool_limit() == 0) {
            if (SurveyItem.getGrade() == grade || SurveyItem.getGrade() == 0) {
                if (SurveyItem.getGender() == gender || SurveyItem.getGender() == 0) {
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
    public boolean checkSurveyHost(String requestUserId, Long activityId) {
        return surveyRepository.getHostByid(activityId).equals(requestUserId);
    }

    /* 上傳* */

    public String saveSurveyPhoto(MultipartFile img, Long id) throws Exception {

        Path path = Paths.get(UPLOADED_FOLDER, Long.toString(id) + getExtension(img));
        img.transferTo(new File(path.toAbsolutePath().toString()));

        return path.toAbsolutePath().toString();
    }

    /**
     * 回傳副檔名 含"."
     * 
     * @param file
     * @return
     */
    private String getExtension(MultipartFile file) throws NullPointerException {
        String originalFilename = file.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf('.'));
    }

}
