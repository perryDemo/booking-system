package com.agoda.clone.agoda.controller;

import com.agoda.clone.agoda.dto.AuthenticationResponse;
import com.agoda.clone.agoda.dto.LoginRequest;
import com.agoda.clone.agoda.dto.RefreshTokenRequest;
import com.agoda.clone.agoda.dto.RegisterRequest;
import com.agoda.clone.agoda.service.AccountService;
import com.agoda.clone.agoda.service.RefreshTokenService;

import org.springframework.http.HttpStatus;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;
    private RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        return accountService.signup(registerRequest);
    }

    @PostMapping("/login")
    public  AuthenticationResponse login(@RequestBody LoginRequest loginRequset){
        return accountService.login(loginRequset);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        accountService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successully", HttpStatus.OK);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return accountService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
    }
}
