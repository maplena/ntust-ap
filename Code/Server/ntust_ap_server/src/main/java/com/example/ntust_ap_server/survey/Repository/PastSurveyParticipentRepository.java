package com.example.ntust_ap_server.survey.Repository;

import com.example.ntust_ap_server.survey.Item.PastSurveyParticipentItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface PastSurveyParticipentRepository extends JpaRepository<PastSurveyParticipentItem, Long>{

    // 以問卷Id尋找
    @Query(value = "SELECT * FROM pastsurveyparticipents WHERE surveyId =?1", countQuery = "SELECT count(*) FROM pastsurveyparticipents WHERE surveyId =?1", nativeQuery = true)
    Page<PastSurveyParticipentItem> findBySurveyId(Long surveyId, Pageable page);

    @Query(value = "SELECT * FROM pastsurveyparticipents WHERE userId =?1", countQuery = "SELECT count(*) FROM pastsurveyparticipents WHERE userId =?1", nativeQuery = true)
    Page<PastSurveyParticipentItem> findByUserId(String userId, Pageable page);

    @Query(value = "SELECT * FROM pastsurveyparticipents WHERE surveyId =?1 and userId =?2", countQuery = "SELECT count(*) FROM pastsurveyparticipents WHERE surveyId =?1 and userId =?2", nativeQuery = true)
    PastSurveyParticipentItem findBySurveyIdandUserId(Long surveyId, String userId);
    
}
