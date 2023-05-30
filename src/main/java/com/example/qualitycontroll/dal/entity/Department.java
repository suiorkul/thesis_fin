package com.example.qualitycontroll.dal.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "departments")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Department extends AbstractModel<Long>{

    String name;

    String description;

}
