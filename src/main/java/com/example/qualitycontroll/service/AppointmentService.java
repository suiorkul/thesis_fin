package com.example.qualitycontroll.service;

import com.example.qualitycontroll.dal.entity.Appointment;
import com.example.qualitycontroll.dal.entity.User;
import com.example.qualitycontroll.dal.enums.Role;
import com.example.qualitycontroll.dal.repository.AppointmentRepository;
import com.example.qualitycontroll.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService extends AbstractService<Appointment, Long>{
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    private static final int PAGE_SIZE = 10;

    public Page<Appointment> getByPatientOrDoctor(String username, Integer pageNumber) {
        PageRequest pageRequest =
                PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "created");

        User user = userRepository.findByUsername(username);
        if (user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.RECEPTIONIST)) {
            return appointmentRepository.findAll(pageRequest);
        }

        return appointmentRepository.getAppointmentsByPatientOrDoctor(user, user, pageRequest);
    }

    @Override
    protected JpaRepository<Appointment, Long> getRepository() {
        return appointmentRepository;
    }
}
