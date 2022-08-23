package com.agoda.clone.agoda.service;

import com.agoda.clone.agoda.dto.ChangePWRequest;
import com.agoda.clone.agoda.dto.LoginRequest;
import com.agoda.clone.agoda.dto.RegisterRequest;
import com.agoda.clone.agoda.dto.UserResponse;
import com.agoda.clone.agoda.model.User;

import org.springframework.http.ResponseEntity;

public interface AccountService {
    public ResponseEntity<String> signup (RegisterRequest registerRequest);
    public void verifyAccount(String token);
    public ResponseEntity<?> login(LoginRequest loginRequset);
    public ResponseEntity<?> logout(String refreshToken);
    public ResponseEntity<?> refreshToken(String refreshToken, int userID);
    public User getCurrentUser();
    public UserResponse getUserDetail();
    public ResponseEntity<String> updateName (String firstName, String lastName);
    public ResponseEntity<String> changePW (ChangePWRequest changePWRequest);
    public ResponseEntity<String> changePhone (String phone, String countrycode);
    public ResponseEntity<String> resendAccountVerificationMail (int id);
}
