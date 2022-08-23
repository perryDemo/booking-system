package com.agoda.clone.agoda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePWRequest {
    private String currentPw;
    private String newPW;
}
