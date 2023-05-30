package com.example.qualitycontroll.dal.enums;

import lombok.Getter;

@Getter
public enum Status {

    REGISTERED("Анализдер Тапшырылган"),
    SENT("Анализдер Келген"),
    RECEIVED("Докторго жазылган"),
    NOTIFIED("Диагноз коюлган");

    public final String label;

    Status(String label) {
        this.label = label;
    }
}
