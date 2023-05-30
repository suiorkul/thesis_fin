package com.example.qualitycontroll.dal.enums;

import lombok.Getter;
import org.hibernate.annotations.GeneratorType;

@Getter
public enum MaterialStatus {
    SINGLE("Бойдок"),
    MARRIED("Уйлонгон"),
    DIVORCED("Ажырашты"),
    WIDOWED("Жесир");

    private String label;

    MaterialStatus(String label) {
        this.label = label;
    }
}
