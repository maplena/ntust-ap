package com.example.ntust_ap_server.activity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ntust_ap_server.activity.Item.ReasonKick;

@Repository
@Transactional(readOnly = true)
public interface ReasonRepositoryKick extends JpaRepository<ReasonKick, Long>{
    
}
