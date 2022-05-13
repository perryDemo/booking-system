package com.agoda.clone.agoda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String lastname;
    private String firstname;
    private String email;
    private String password;
}
