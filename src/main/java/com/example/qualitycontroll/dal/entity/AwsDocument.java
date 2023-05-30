package com.example.qualitycontroll.dal.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "document")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true, fluent = true)
public class AwsDocument extends AbstractModel<Long>{

    String originalFileName;

    String fileName;

    String fileUrl;

}
