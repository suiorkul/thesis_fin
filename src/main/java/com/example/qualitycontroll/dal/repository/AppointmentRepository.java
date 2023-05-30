package com.example.qualitycontroll.dal.repository;

import com.amazonaws.services.fms.model.App;
import com.example.qualitycontroll.dal.entity.Appointment;
import com.example.qualitycontroll.dal.entity.Patient;
import com.example.qualitycontroll.dal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> getAppointmentsByPatientOrDoctor(User patient, User doctor, PageRequest pageRequest);

}
