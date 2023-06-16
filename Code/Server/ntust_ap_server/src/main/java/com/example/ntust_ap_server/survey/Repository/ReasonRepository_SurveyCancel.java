package com.example.ntust_ap_server.survey.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ntust_ap_server.survey.Item.ReasonSurveyCancelItem;

@Repository
@Transactional(readOnly = true)
public interface ReasonRepository_SurveyCancel extends JpaRepository<ReasonSurveyCancelItem, Long>{
    
}
