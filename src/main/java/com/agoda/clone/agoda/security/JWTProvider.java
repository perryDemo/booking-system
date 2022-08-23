package com.agoda.clone.agoda.security;

import java.time.Instant;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static java.util.Date.from;
@Service
public class JWTProvider {

    @Value("${jwt.access.expiration.time}")
    private Long jwtAccessExpirationInMillis;
    @Value("${jwt.refresh.expiration.time}")
    private Long jwtRefreshEExpirationInMillis;
    
    public String generateToken(int id){
        return JWT.create()
            .withSubject(""+id)
            .withExpiresAt(new Date(System.currentTimeMillis()+jwtAccessExpirationInMillis))
            .withIssuedAt(from(Instant.now()))
            .withIssuer("agoda")
            .sign(Algorithm.HMAC256("secret".getBytes()));
    }

    public String generateRefreshToken(String token){
        return JWT.create()
            .withSubject(token)
            .withExpiresAt(new Date(System.currentTimeMillis()+jwtRefreshEExpirationInMillis))
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

    public String getSubjectFromJWT (String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }

    public Long getJwtAccessExpirationInMillis(){
        return jwtAccessExpirationInMillis;
    }

    public Long getJwtRefreshEExpirationInMillis(){
        return jwtRefreshEExpirationInMillis;
    }
}
