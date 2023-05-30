package com.example.qualitycontroll.dal.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthCredentialsRequest {
    String username;
    String password;
}
