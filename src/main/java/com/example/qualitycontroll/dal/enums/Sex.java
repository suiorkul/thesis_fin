package com.example.qualitycontroll.dal.enums;

import lombok.Getter;

@Getter
public enum Sex {
    MALE("Эркек"),
    FEMALE("Аял");

    public final String label;

    Sex(String label) {
        this.label = label;
    }
}
