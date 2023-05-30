package com.example.qualitycontroll.util;


import com.example.qualitycontroll.dal.entity.Appointment;
import com.example.qualitycontroll.dal.entity.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WhatsAppUtils {

    @Value("${link.url}")
    private String linkUrl;

    public String registerPatientMessage(Patient patient, String username, String password) {
        return String.format("Саламатсызбы %s %s!\n" +
                "Сиздин биздин ооруканада катталдыныз\n" +
                "Шилтеме менен отуп кирип озунуздун даарыланууз жонундо маалымат алсаныз болот: %s\n" +
                "Логин: %s\n" +
                "Пароль: %s", patient.getFirstName(), patient.getLastName(), linkUrl, username, password);
    }

    public String cancelAppointmentMessage(Appointment appointment) {
        return String.format("Урматтуу %s %s\n" +
                        "Сиздин  %s %s ,  %s %s доктурга болгон жазылуунуз жокко чыгарылды",
                appointment.getPatient().getFirstname(),
                appointment.getPatient().getLastname(), appointment.getDate(),
                appointment.getTime(), appointment.getDoctor().getFirstname(),
                appointment.getDoctor().getLastname());
    }

    ;

    public String registerAppointmentMessage(Appointment appointment) {
        return String.format("Урматтуу %s %s\n" +
                        "Сизди %s %s,  %s %s доктурга жазылдыныз",
                appointment.getPatient().getFirstname(),
                appointment.getPatient().getLastname(), appointment.getDate(),
                appointment.getTime(), appointment.getDoctor().getFirstname(),
                appointment.getDoctor().getLastname());
    }

}
