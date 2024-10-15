package com.recruitease.joblisting.repository;


import com.recruitease.joblisting.model.Field;
import com.recruitease.joblisting.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FieldRepository extends JpaRepository<Field, Integer> {




}
