package com.agoda.clone.agoda.controller;

import com.agoda.clone.agoda.dto.ChangePWRequest;
import com.agoda.clone.agoda.dto.LoginRequest;
import com.agoda.clone.agoda.dto.RegisterRequest;
import com.agoda.clone.agoda.dto.UserResponse;
import com.agoda.clone.agoda.service.AccountService;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CookieValue;
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

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        return accountService.signup(registerRequest);
    }

    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody LoginRequest loginRequset){
        return accountService.login(loginRequset);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        accountService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successully", HttpStatus.OK);
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<?> refreshTokens(@Nullable @CookieValue(value = "refresh") String refreshToken,@RequestBody String userID) {
        if(refreshToken!=null)
            return accountService.refreshToken(refreshToken, Integer.parseInt(userID));
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Nullable @CookieValue(value = "refresh") String refreshToken) {
        if(refreshToken!=null)
        return accountService.logout(refreshToken);
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/resendAccountVerificationMail")
    public ResponseEntity<String> resendAccountVerificationMail(String id) {
        return accountService.resendAccountVerificationMail(Integer.parseInt(id));
    }

    @GetMapping("/profile/userProfile")
    public UserResponse getUserProfile(){
        return accountService.getUserDetail();
    }
    
    @PostMapping("/profile/updateName")
    public ResponseEntity<String> updateName (@Valid @RequestBody String name){
        JSONObject jsonName = new JSONObject(name);
        return accountService.updateName(jsonName.getString("firstName"), jsonName.getString("lastName"));
    }
    @PostMapping("/profile/changePW")
    public ResponseEntity<String> changePW (@Valid @RequestBody ChangePWRequest changePWRequest){
        return accountService.changePW(changePWRequest);
    }

    @PostMapping("/profile/changePhone")
    public ResponseEntity<String> changePhone (@Valid @RequestBody String contact){
        JSONObject jsonContact = new JSONObject(contact);
        return accountService.changePhone(jsonContact.getString("phone"), jsonContact.getString("countryCode"));
    }
}
