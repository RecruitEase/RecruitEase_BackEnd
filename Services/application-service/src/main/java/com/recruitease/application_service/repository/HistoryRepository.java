package com.recruitease.application_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.recruitease.application_service.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

    History findByApplicationId(String applicationId);

}
