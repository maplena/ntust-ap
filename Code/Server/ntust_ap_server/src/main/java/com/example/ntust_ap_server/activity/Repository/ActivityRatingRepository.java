package com.example.ntust_ap_server.activity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import com.example.ntust_ap_server.activity.Item.ActivityRatingItem;

@Repository
@Transactional(readOnly = true)
public interface ActivityRatingRepository extends JpaRepository<ActivityRatingItem, Long> {

    Optional<ActivityRatingItem> findByid(Long id);

    /**
     * 依照該activity去找評價列表
     * 
     * @param activityId
     * @param page
     * @return
     */
    @Query(value = "SELECT * FROM activities_rating WHERE activityId =?1 ", countQuery = "SELECT count(*) FROM activities_rating WHERE activityId = ?1", nativeQuery = true)
    Page<ActivityRatingItem> getListByActivityId(Long activityId, Pageable page);

    @Query(value = "SELECT Id FROM activities_rating WHERE activityId =?1 and userId =?2", nativeQuery = true)
    Long findByUserId(Long activityId, String userId);

    /**
     * 回傳該活動平均評分
     * 
     * @param activityId
     * @return Double 評分
     */
    @Query(value = "SELECT avg(rating) FROM activities_rating WHERE activityId =?1", nativeQuery = true)
    Double getActivityAvgRating(Long activityId);

}
