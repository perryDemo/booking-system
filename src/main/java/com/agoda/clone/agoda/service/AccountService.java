package com.agoda.clone.agoda.service;

import com.agoda.clone.agoda.dto.AuthenticationResponse;
import com.agoda.clone.agoda.dto.LoginRequest;
import com.agoda.clone.agoda.dto.RefreshTokenRequest;
import com.agoda.clone.agoda.dto.RegisterRequest;
import com.agoda.clone.agoda.dto.UserResponse;
import com.agoda.clone.agoda.model.User;

import org.springframework.http.ResponseEntity;

public interface AccountService {
    public ResponseEntity<String> signup (RegisterRequest registerRequest);
    public void verifyAccount(String token);
    public AuthenticationResponse login(LoginRequest loginRequset);
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    public User getCurrentUser();
    public UserResponse getUserDetail(LoginRequest loginRequest);
}
