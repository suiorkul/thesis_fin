package com.example.qualitycontroll.dal.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends AbstractModel<Long>{

    @ManyToOne()
            @JoinColumn(name = "patient", referencedColumnName = "id")
    User patient;

    @ManyToOne()
            @JoinColumn(name = "doctor", referencedColumnName = "id")
    User doctor;

    String phoneNumber;

    String time;

    String date;

    String address;

    String deceased;

}
