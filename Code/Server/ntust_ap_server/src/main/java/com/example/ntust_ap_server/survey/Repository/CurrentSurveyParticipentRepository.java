package com.example.ntust_ap_server.survey.Repository;

import java.util.LinkedList;

import com.example.ntust_ap_server.survey.Item.CurrentSurveyParticipentItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface CurrentSurveyParticipentRepository extends JpaRepository<CurrentSurveyParticipentItem, Long> {

    /**
     * 以問卷Id尋找
     * 
     * @param surveyId
     * @param page
     * @return
     */
    @Query(value = "SELECT * FROM currentsurveyparticipents WHERE surveyId =?1", countQuery = "SELECT count(*) FROM currentsurveyparticipents WHERE surveyId =?1", nativeQuery = true)
    Page<CurrentSurveyParticipentItem> findBySurveyId(Long surveyId, Pageable page);

    /**
     * 取得所有參與者
     * 
     * @param surveyId
     * @return
     */
    @Query(value = "SELECT * FROM currentsurveyparticipents WHERE surveyId =?1", countQuery = "SELECT count(*) FROM currentsurveyparticipents WHERE surveyId =?1", nativeQuery = true)
    LinkedList<CurrentSurveyParticipentItem> getAlluserBySurveyId(Long surveyId);

    /**
     * 取得該user所參加的所有問卷
     * 
     * @param userId
     * @param page
     * @return
     */
    @Query(value = "SELECT * FROM currentsurveyparticipents WHERE userId =?1", countQuery = "SELECT count(*) FROM currentsurveyparticipents WHERE userId =?1", nativeQuery = true)
    Page<CurrentSurveyParticipentItem> findByUserId(String userId, Pageable page);

    /**
     * 取得特定參與物件
     * 
     * @param surveyId
     * @param userId
     * @return
     */
    @Query(value = "SELECT * FROM currentsurveyparticipents WHERE surveyId =?1 and userId =?2", countQuery = "SELECT count(*) FROM currentsurveyparticipents WHERE surveyId =?1 and userId =?2", nativeQuery = true)
    CurrentSurveyParticipentItem findBySurveyIdandUserId(Long surveyId, String userId);

}
