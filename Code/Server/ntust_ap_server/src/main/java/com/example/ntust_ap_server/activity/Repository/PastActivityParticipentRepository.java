package com.example.ntust_ap_server.activity.Repository;

import java.util.List;

import com.example.ntust_ap_server.activity.Item.PastActivityParticipentItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)//這邊有個問題 extend的entity需要分成past與current嗎?
public interface PastActivityParticipentRepository extends JpaRepository<PastActivityParticipentItem, Long>{
    // 以活動Id尋找
    @Query(value = "SELECT * FROM pastactivityparticipents WHERE activityId =?1", countQuery = "SELECT count(*) FROM pastactivityparticipents WHERE activityId =?1", nativeQuery = true)
    Page<PastActivityParticipentItem> findByActivityId(Long activityId, Pageable page);

    // @Query(value = "SELECT activityId FROM pastactivityparticipents WHERE userId =?1
    // and status =?3", countQuery = "SELECT count(*) FROM pastactivityparticipents
    // WHERE userId =?1 and status =?3", nativeQuery = true)
    // Page<BigInteger> findByUserIdandStatus(String userId, Pageable page,String
    // status);

    @Query(value = "SELECT * FROM pastactivityparticipents WHERE userId =?1 ", countQuery = "SELECT count(*) FROM pastactivityparticipents WHERE userId =?1", nativeQuery = true)
    Page<PastActivityParticipentItem> findByUserId(String userId, Pageable page);

    @Query(value = "SELECT activityId FROM pastactivityparticipents WHERE userId =?1 ", countQuery = "SELECT count(*) FROM pastactivityparticipents WHERE userId =?1", nativeQuery = true)
    List<Long> getActivityIdByUserId(String userId, Pageable page);

    @Query(value = "SELECT * FROM pastactivityparticipents WHERE activityId =?1 and userId =?2", countQuery = "SELECT count(*) FROM pastactivityparticipents WHERE activityId =?1 and userId =?2", nativeQuery = true)
    PastActivityParticipentItem findByActivityIdandUserId(Long activityId, String userId);
}
