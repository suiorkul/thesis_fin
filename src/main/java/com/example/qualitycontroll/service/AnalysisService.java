package com.example.qualitycontroll.service;

import com.example.qualitycontroll.dal.entity.Analysis;
import com.example.qualitycontroll.dal.repository.AnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalysisService extends AbstractService<Analysis, Long>{

    private final AnalysisRepository analysisRepository;

    @Override
    protected JpaRepository<Analysis, Long> getRepository() {
        return analysisRepository;
    }
}
