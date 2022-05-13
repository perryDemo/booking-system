package com.agoda.clone.agoda.repository;

import java.util.Optional;

import com.agoda.clone.agoda.model.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
