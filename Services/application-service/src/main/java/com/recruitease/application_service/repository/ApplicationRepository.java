package com.recruitease.application_service.repository;

import com.recruitease.application_service.entity.Application;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {
    //to find whether theres a entry with given candidate id and job id
    boolean existsByCandidateIdAndJobIdAndStatusNot(String candidateId, String jobId,String status);

    //to find whether theres a entry with given candidateId and applicatioonId
    boolean existsByCandidateIdAndApplicationId(String candidateId, String applicationId);


    List<Application> findByCandidateId(String candidateId);
    List<Application> findByJobId(String jobId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE application SET status = :status WHERE application_id = :applicationId",nativeQuery = true)
    int updateStatusByApplicationId(String status, String applicationId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE application SET status = :status WHERE application_id IN :applicationIds", nativeQuery = true)
    int updateStatusByApplicationIds(String status, List<String> applicationIds);

}
