package com.example.qualitycontroll.dal.repository;

import com.example.qualitycontroll.dal.entity.Analysis;
import com.example.qualitycontroll.dal.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    List<Analysis> findAllByPatient(Patient patient);
    void deleteAllByPatient(Patient patient);
}
