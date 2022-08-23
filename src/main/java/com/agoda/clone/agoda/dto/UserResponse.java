package com.agoda.clone.agoda.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String username;
    private String firstName;
    private String lastName;
    private boolean verification;
    private Instant createDate;
    private String phone;
    private String countrycode;
    private AuthenticationResponse authenticationResponse;
}
