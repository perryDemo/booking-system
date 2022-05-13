package com.agoda.clone.agoda.security;

import java.time.Instant;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import static java.util.Date.from;
@Service
public class JWTProvider {

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;
    
    public String generateToken(Authentication authentication){
        org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
        return JWT.create()
            .withSubject(principal.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis()+jwtExpirationInMillis))
            .withIssuedAt(from(Instant.now()))
            .withIssuer("agoda")
            .sign(Algorithm.HMAC256("secret".getBytes()));
    }

    public String generateTokenWithUserName(String username){
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(new Date(System.currentTimeMillis()+jwtExpirationInMillis))
            .withIssuedAt(from(Instant.now()))
            .withIssuer("agoda")
            .sign(Algorithm.HMAC256("secret".getBytes()));
    }

    public boolean validateToken(String jwt){
        try{
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("agoda")
                .build();
            verifier.verify(jwt);
            return true;
        } catch (JWTVerificationException exception) {
            System.out.println("error:"+exception);
        }
        return false;
    }

    public String getUsernameFromJWT (String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }

    public Long getJwtExpirationInMillis(){
        return jwtExpirationInMillis;
    }
}
