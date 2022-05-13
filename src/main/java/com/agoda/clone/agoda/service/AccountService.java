package com.agoda.clone.agoda.service;

import com.agoda.clone.agoda.dto.AuthenticationResponse;
import com.agoda.clone.agoda.dto.LoginRequest;
import com.agoda.clone.agoda.dto.RefreshTokenRequest;
import com.agoda.clone.agoda.dto.RegisterRequest;

import org.springframework.http.ResponseEntity;

public interface AccountService {
    public ResponseEntity<String> signup (RegisterRequest registerRequest);
    public void verifyAccount(String token);
    public AuthenticationResponse login(LoginRequest loginRequset);
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
