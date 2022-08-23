package com.agoda.clone.agoda.service;

import java.time.Period;
import java.util.Optional;
import java.util.UUID;

import com.agoda.clone.agoda.dto.AuthenticationResponse;
import com.agoda.clone.agoda.dto.ChangePWRequest;
import com.agoda.clone.agoda.dto.LoginRequest;
import com.agoda.clone.agoda.dto.RegisterRequest;
import com.agoda.clone.agoda.dto.UserResponse;
import com.agoda.clone.agoda.mapper.UserMapper;
import com.agoda.clone.agoda.model.User;
import com.agoda.clone.agoda.model.VerificationToken;
import com.agoda.clone.agoda.repository.UserRepository;
import com.agoda.clone.agoda.repository.VerificationTokenRepository;
import com.agoda.clone.agoda.security.JWTProvider;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private UserMapper userMapper;

    @Override
    public ResponseEntity<String> signup(RegisterRequest registerRequest) {
        if(!userRepository.existsByEmail(registerRequest.getEmail())){
            User user = new User();
            user.setLastname(registerRequest.getLastname());
            user.setFirstname(registerRequest.getFirstname());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setPhone("");
            user.setCountrycode("");
            user.setVerification(false);
            user.setCreatedat(java.time.Instant.now());
            user.setCreatedby("signup");
            user.setModifiedat(java.time.Instant.now());
            user.setModifiedby("signup");
            user.setDeletedat(java.time.Instant.ofEpochMilli(0));
            user.setDeletedby("");
            userRepository.save(user);
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
        verificationTokenOptional.orElseThrow();
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getEmail();
        User user = userRepository.findByEmail(username).orElseThrow();
        user.setVerification(true);
        userRepository.save(user);
    }
    
    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User name not found - " + loginRequest.getUsername()));
        String token = jwtProvider.generateToken(user.getId());
        ResponseCookie cookie = ResponseCookie.from("agoda", token).path("/").secure(true).maxAge( 15*60 ).httpOnly(true).sameSite("None").build();
        String refreshToken = jwtProvider.generateRefreshToken(refreshTokenService.generateRefreshToken().getToken());
        ResponseCookie refreshCookie = ResponseCookie.from("refresh", refreshToken).path("/").secure(true).maxAge( 24*60*60 ).httpOnly(true).sameSite("None").build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString(), refreshCookie.toString())
            .body(AuthenticationResponse.builder()
            .id(user.getId())
            .lastName(user.getLastname())
            .firstName(user.getFirstname()).build());
    }

    
    @Override
    public ResponseEntity<?> logout(String refreshToken) {
        refreshTokenService.deleteRefreshToken(jwtProvider.getSubjectFromJWT(refreshToken));
        ResponseCookie cookie = ResponseCookie.from("agoda","").path("/").secure(true).maxAge( 0 ).httpOnly(true).sameSite("None").build();
        ResponseCookie refreshCookie = ResponseCookie.from("refresh", "").path("/").secure(true).maxAge( 0).httpOnly(true).sameSite("None").build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString(), refreshCookie.toString())
        .body(cookie.toString());
    }
     

    @Override
    public ResponseEntity<?> refreshToken(String refreshToken, int userID) {
        refreshTokenService.validateRefreshToken(jwtProvider.getSubjectFromJWT(refreshToken));
        User user = userRepository.findById(userID).orElseThrow(() -> new UsernameNotFoundException("User name not found - " + userID));
        String token = jwtProvider.generateToken(user.getId());
        ResponseCookie cookie = ResponseCookie.from("agoda", token).path("/").secure(true).maxAge( 15*60 ).httpOnly(true).sameSite("None").build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(AuthenticationResponse.builder()
            .id(user.getId())
            .lastName(user.getLastname())
            .firstName(user.getFirstname()).build());
    }
    @Override
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    @Override
    public UserResponse getUserDetail() {
        // TODO Auto-generated method stub
        return userMapper.mapToUserResponse(getCurrentUser());

    }

    @Override
    public ResponseEntity<String> updateName(String firstName, String lastName) {
        // TODO Auto-generated method stub
        User user = getCurrentUser();
        user.setFirstname(firstName);
        user.setLastname(lastName);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePW(ChangePWRequest changePWRequest) {
        // TODO Auto-generated method stub
        User user = getCurrentUser();
        if(passwordEncoder.matches(changePWRequest.getCurrentPw(),user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePWRequest.getNewPW()));
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @Override
    public ResponseEntity<String> changePhone(String phone, String countrycode) {
        // TODO Auto-generated method stub
        User user = getCurrentUser();
        user.setPhone(phone);
        user.setCountrycode(countrycode);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> resendAccountVerificationMail(int id) {
        User user = getCurrentUser();
        VerificationToken token2 = verificationTokenRepository.findByUser(user).orElseThrow();;
        verificationTokenRepository.delete(token2);
        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, " +
        "please click on the below url to activate your account : " +
        "http://localhost:8082/api/account/accountVerification/" + token);

        mailService.sendMail(user.getEmail(), "Please Activate your account", message);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
