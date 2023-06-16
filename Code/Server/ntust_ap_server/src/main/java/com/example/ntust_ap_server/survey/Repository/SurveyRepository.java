package com.example.ntust_ap_server.survey.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.example.ntust_ap_server.survey.Item.SurveyItem;

@Repository
@Transactional(readOnly = true)
//增加page pageable 實現分頁功能//10Dec2021//CLH
public interface SurveyRepository extends JpaRepository<SurveyItem, Long> {
        


    Optional<SurveyItem> findById(Long Id); // 以ID為鍵值

    @Query(value = "SELECT * FROM surveys WHERE (school_limit =?1 or school_limit = 0) and (grade =?2 or grade = 0) and (gender =?3 or gender = 0) and status =?4", countQuery = "SELECT count(*) FROM surveys WHERE (school_limit =?1 or school_limit = 0) and (grade =?2 or grade = 0) and (gender =?3 or gender = 0) and status =?4", nativeQuery = true)
    Page<SurveyItem> findBylimit(int school, int grade, int gender, String status, Pageable page);


    //依tag找 
    @Query(value = "SELECT * FROM surveys WHERE tag LIKE ?1 and status =?2", countQuery = "SELECT count(*) FROM surveys WHERE tag LIKE ?1 and status =?2", nativeQuery = true)
    Page<SurveyItem> findBytag(String tag, String status, Pageable page);

    //依舉辦者找
    @Query(value = "SELECT * FROM surveys WHERE hostID =?1 and status =?2", countQuery = "SELECT count(*) FROM surveys WHERE hostID = ?1 and status =?2", nativeQuery = true)
    Page<SurveyItem> findByHostId(String hostId, String status, Pageable page);

    @Query(value = "SELECT status FROM surveys WHERE id =?1 ", nativeQuery = true)
    String getStatusByid(Long surveyId);

    @Query(value = "SELECT hostID FROM surveys WHERE id =?1", nativeQuery = true)
    String getHostByid(Long surveyId);
}
