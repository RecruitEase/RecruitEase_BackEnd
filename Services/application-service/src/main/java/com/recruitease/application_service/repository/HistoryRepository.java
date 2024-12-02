package com.recruitease.application_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.recruitease.application_service.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, java.util.UUID> {

    List<History> findByApplicationId(String applicationId);

    boolean existsByApplicationId(String applicationId);

}
