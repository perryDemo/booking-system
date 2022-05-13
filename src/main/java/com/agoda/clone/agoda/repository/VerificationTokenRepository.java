package com.agoda.clone.agoda.repository;

import java.util.Optional;

import com.agoda.clone.agoda.model.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer>{
    Optional<VerificationToken> findByToken(String token);
}
