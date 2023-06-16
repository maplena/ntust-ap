package com.example.ntust_ap_server.lottery.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.example.ntust_ap_server.lottery.Item.LotteryItem;

@Repository
@Transactional(readOnly = true)
//增加page pageable 實現分頁功能//10Dec2021//CLH
public interface LotteryRepository extends JpaRepository<LotteryItem, Long> {
        


    Optional<LotteryItem> findById(Long Id); // 以ID為鍵值

    // @Query(value = "SELECT * FROM lottery WHERE (school_limit =?1 or school_limit = 0) and (grade =?2 or grade = 0) and (gender =?3 or gender = 0) and status =?4", countQuery = "SELECT count(*) FROM lottery WHERE (school_limit =?1 or school_limit = 0) and (grade =?2 or grade = 0) and (gender =?3 or gender = 0) and status =?4", nativeQuery = true)
    // Page<LotteryItem> findBylimit(int school, int grade, int gender, String status, Pageable page);

    @Query(value = "SELECT * FROM lottery WHERE status =?1", countQuery = "SELECT count(*) FROM lottery WHERE status =?1", nativeQuery = true)
    Page<LotteryItem> findAvailable(String status, Pageable page);

    //依tag找 
    @Query(value = "SELECT * FROM lottery WHERE tag LIKE ?1 and status =?2", countQuery = "SELECT count(*) FROM lottery WHERE tag LIKE ?1 and status =?2", nativeQuery = true)
    Page<LotteryItem> findBytag(String tag, String status, Pageable page);

    //依舉辦者找
    @Query(value = "SELECT * FROM lottery WHERE hostID =?1 and status =?2", countQuery = "SELECT count(*) FROM lottery WHERE hostID = ?1 and status =?2", nativeQuery = true)
    Page<LotteryItem> findByHostId(String hostId, String status, Pageable page);

    @Query(value = "SELECT status FROM lottery WHERE id =?1 ", nativeQuery = true)
    String getStatusByid(Long lotteryId);

    @Query(value = "SELECT probability FROM lottery WHERE id =?1 ", nativeQuery = true)
    Float getProbabilityByid(Long lotteryId);

    @Query(value = "SELECT hostID FROM lottery WHERE id =?1", nativeQuery = true)
    String getHostByid(Long lotteryId);
}
