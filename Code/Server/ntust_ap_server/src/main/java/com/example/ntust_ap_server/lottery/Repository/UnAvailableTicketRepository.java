package com.example.ntust_ap_server.lottery.Repository;

import java.util.LinkedList;

import com.example.ntust_ap_server.lottery.Item.UnAvailableTicketItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UnAvailableTicketRepository extends JpaRepository<UnAvailableTicketItem, Long> {

    /**
     * 以問卷Id尋找
     * 
     * @param lotteryId
     * @param page
     * @return
     */
    @Query(value = "SELECT * FROM availableticket WHERE LotteryId =?1", countQuery = "SELECT count(*) FROM availableticket WHERE LotteryId =?1", nativeQuery = true)
    Page<UnAvailableTicketItem> findByLotteryId(Long lotteryId, Pageable page);

    /**
     * 取得所有參與者
     * 
     * @param lotteryId
     * @return
     */
    @Query(value = "SELECT * FROM availableticket WHERE LotteryId =?1", countQuery = "SELECT count(*) FROM availableticket WHERE LotteryId =?1", nativeQuery = true)
    LinkedList<UnAvailableTicketItem> getAlluserByLotteryId(Long lotteryId);

    /**
     * 取得該user所參加的所有問卷
     * 
     * @param userId
     * @param page
     * @return
     */
    @Query(value = "SELECT * FROM availableticket WHERE userId =?1", countQuery = "SELECT count(*) FROM availableticket WHERE userId =?1", nativeQuery = true)
    Page<UnAvailableTicketItem> findByUserId(String userId, Pageable page);

    /**
     * 取得特定參與物件
     * 
     * @param lotteryId
     * @param userId
     * @return
     */
    @Query(value = "SELECT * FROM availableticket WHERE LotteryId =?1 and userId =?2", countQuery = "SELECT count(*) FROM availableticket WHERE LotteryId =?1 and userId =?2", nativeQuery = true)
    UnAvailableTicketItem findByLotteryIdandUserId(Long lotteryId, String userId);

}
