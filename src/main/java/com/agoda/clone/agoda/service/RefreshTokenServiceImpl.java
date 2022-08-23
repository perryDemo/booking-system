package com.agoda.clone.agoda.service;

import java.time.Instant;
import java.util.UUID;

import com.agoda.clone.agoda.model.RefreshToken;
import com.agoda.clone.agoda.repository.RefreshTokenRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService{
    
    private RefreshTokenRepository refreshTokenRepository;
    @Override
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreateddate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void validateRefreshToken(String token) {
        try {
            refreshTokenRepository.findByToken(token).orElseThrow(()-> new Exception("error"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.print("no token");
        }        
    }

    @Override
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);        
    }
    
}
