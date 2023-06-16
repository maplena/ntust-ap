package com.example.ntust_ap_server.activity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ntust_ap_server.activity.Item.ReasonActivityCancelItem;

@Repository
@Transactional(readOnly = true)
public interface ReasonRepository_Cancel extends JpaRepository<ReasonActivityCancelItem, Long>{
    
}
