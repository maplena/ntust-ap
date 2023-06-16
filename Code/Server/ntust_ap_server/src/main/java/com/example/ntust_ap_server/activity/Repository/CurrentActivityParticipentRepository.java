package com.example.ntust_ap_server.activity.Repository;

import java.util.LinkedList;
import java.util.List;

import com.example.ntust_ap_server.activity.Item.CurrentActivityParticipentItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)

// 參加人員DAO 延伸自JPA<參加人員entity,主鍵類(此處為多重主鍵)>
public interface CurrentActivityParticipentRepository extends JpaRepository<CurrentActivityParticipentItem, Long> {

    //以活動Id尋找
    @Query(value = "SELECT * FROM currentactivityparticipents WHERE activityId =?1 ", countQuery = "SELECT count(*) FROM currentactivityparticipents WHERE activityId =?1", nativeQuery = true)
    Page<CurrentActivityParticipentItem> findByActivityId(Long activityId, Pageable page);

    @Query(value = "SELECT COUNT(Id) FROM currentactivityparticipents WHERE activityId =?1 ",nativeQuery = true)
    int getNumberByActivityId(Long activityId);

    //以活動Id尋找 但返還所有相符結果 並以Entity格式 裝進iterable的陣列
    @Query(value = "SELECT * FROM currentactivityparticipents WHERE activityId =?1", countQuery = "SELECT count(*) FROM currentactivityparticipents WHERE activityId =?1", nativeQuery = true)
    LinkedList<CurrentActivityParticipentItem> getAlluserByActivityId(Long activityId);

    // @Query(value = "SELECT activityId FROM activityparticipents WHERE userId =?1 and status =?3", countQuery = "SELECT count(*) FROM activityparticipents WHERE userId =?1 and status =?3", nativeQuery = true)
    // Page<BigInteger> findByUserIdandStatus(String userId, Pageable page,String status);

    @Query(value = "SELECT activityId FROM currentactivityparticipents WHERE userId =?1 ", countQuery = "SELECT count(*) FROM currentactivityparticipents WHERE userId =?1", nativeQuery = true)
    Page<Long> findByUserId(String userId, Pageable page);

    @Query(value = "SELECT activityId FROM currentactivityparticipents WHERE userId =?1 ", countQuery = "SELECT count(*) FROM currentactivityparticipents WHERE userId =?1", nativeQuery = true)
    List<Long> findByUserId(String userId);

    @Query(value = "SELECT * FROM currentactivityparticipents WHERE activityId =?1 and userId =?2", countQuery = "SELECT count(*) FROM currentactivityparticipents WHERE activityId =?1 and userId =?2", nativeQuery = true)
    CurrentActivityParticipentItem findByActivityIdandUserId(Long activityId, String userId);

}
