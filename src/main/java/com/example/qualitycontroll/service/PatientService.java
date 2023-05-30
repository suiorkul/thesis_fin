package com.example.qualitycontroll.service;

import com.example.qualitycontroll.dal.entity.Patient;
import com.example.qualitycontroll.dal.repository.AnalysisRepository;
import com.example.qualitycontroll.dal.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientService extends AbstractService<Patient, Long>{

    private final PatientRepository patientRepository;
    private final AnalysisRepository analysisRepository;

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            analysisRepository.deleteAllByPatient(patientRepository.getPatientById(id));
            getRepository().deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
    }

    @Override
    protected JpaRepository<Patient, Long> getRepository() {
        return patientRepository;
    }
}
