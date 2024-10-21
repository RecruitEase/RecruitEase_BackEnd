package com.recruitease.application_service.repository;

import com.recruitease.application_service.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {

    List<Offer> findByCandidateId(String candidateId);

    List<Offer> findByRecruiterId(String recruiterId);

    List<Offer> findByStatus(Offer.OfferStatus offerStatus);
}
