package com.example.qualitycontroll.dal.enums;
import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    DOCTOR("DOCTOR"),
    RECEPTIONIST("RECEPTIONIST"),
    PATIENT("PATIENT");

    private String label;

    Role(String label) {
        this.label = label;
    }
}
