package com.cv_service.cv_service.repository;

import com.cv_service.cv_service.DTO.CvSetResponseDTO;
import com.cv_service.cv_service.entity.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvRepo extends JpaRepository<Cv, String> {
    Boolean existsByCvNameAndCandidateId(String cvName, String  candidateId);

    List<Cv> findByCandidateId(String candidateId);

    List<Cv> findByCvIdIn (List<String> cvIds);

    List<Cv> findByCandidateIdAndIsDeletedFalse(String candidateId);

}
