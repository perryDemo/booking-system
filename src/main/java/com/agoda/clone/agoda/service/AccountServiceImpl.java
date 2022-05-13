package com.agoda.clone.agoda.service;

import java.time.Instant;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;

import com.agoda.clone.agoda.dto.AuthenticationResponse;
import com.agoda.clone.agoda.dto.LoginRequest;
import com.agoda.clone.agoda.dto.RefreshTokenRequest;
import com.agoda.clone.agoda.dto.RegisterRequest;
import com.agoda.clone.agoda.model.User;
import com.agoda.clone.agoda.model.VerificationToken;
import com.agoda.clone.agoda.repository.UserRepository;
import com.agoda.clone.agoda.repository.VerificationTokenRepository;
import com.agoda.clone.agoda.security.JWTProvider;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private MailContentBuilder mailContentBuilder;
    private MailService mailService;
    private AuthenticationManager authenticationManager;
    private JWTProvider jwtProvider;
    private RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<String> signup(RegisterRequest registerRequest) {
        if(!userRepository.existsByEmail(registerRequest.getEmail())){
            User user = new User();
            user.setLastname(registerRequest.getLastname());
            user.setFirstname(registerRequest.getFirstname());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setVerification(false);
            user.setCreatedat(java.time.Instant.now());
            user.setCreatedby("signup");
            user.setModifiedat(java.time.Instant.now());
            user.setModifiedby("signup");
            user.setDeletedat(java.time.Instant.ofEpochMilli(0));
            user.setDeletedby("");
            userRepository.save(user);
            System.out.println(user);
            String token = generateVerificationToken(user);
            String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, " +
            "please click on the below url to activate your account : " +
            "http://localhost:8082/api/account/accountVerification/" + token);

            mailService.sendMail(user.getEmail(), "Please Activate your account", message);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FOUND);

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpirydate(java.time.Instant.now().plus(Period.ofDays(1)));
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        System.out.println(verificationTokenOptional);
        verificationTokenOptional.orElseThrow();
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getEmail();
        System.out.println(userRepository.findByEmail(username));
        User user = userRepository.findByEmail(username).orElseThrow();
        user.setVerification(true);
        userRepository.save(user);
    }
    
    @Override
    public AuthenticationResponse login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return AuthenticationResponse.builder()
            .authenticationToken(token)
            .refreshToken(refreshTokenService.generateRefreshToken().getToken())
            .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
            .username(loginRequest.getUsername())
            .build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername()).build();
    }           
}
