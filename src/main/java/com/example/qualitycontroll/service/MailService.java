package com.example.qualitycontroll.service;

import com.example.qualitycontroll.config.WhatsAppApi;
import com.example.qualitycontroll.dal.entity.Analysis;
import com.example.qualitycontroll.dal.entity.Appointment;
import com.example.qualitycontroll.dal.entity.Patient;
import com.example.qualitycontroll.util.WhatsAppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final WhatsAppApi whatsAppApi;

    private final WhatsAppUtils whatsAppUtils;

    public Analysis sendAnalysis(Analysis analysis) {
        whatsAppApi.sendDocument(analysis.getPatient().getPhoneNumber(), analysis.getDepartmentDocument(), analysis.getDescription());
        return analysis;
    }

    public ResponseEntity<String> sendMessage(String to, String message) {
        return whatsAppApi.sendMessage(to, message);
    }

    public String sendRegistrationSuccess(Patient patient, String username, String password) {
        sendMessage(patient.getPhoneNumber(), whatsAppUtils.registerPatientMessage(patient, username, password));
        return "ok";
    }

    public String sendAppointmentSuccess(Appointment appointment) {
        sendMessage(appointment.getPatient().getPhoneNumber(), whatsAppUtils.registerAppointmentMessage(appointment));
        return "ok";
    }

    public String sendAppointmentCanceled(Appointment appointment) {
        sendMessage(appointment.getPatient().getPhoneNumber(), whatsAppUtils.cancelAppointmentMessage(appointment));
        return "ok";

    }

}
