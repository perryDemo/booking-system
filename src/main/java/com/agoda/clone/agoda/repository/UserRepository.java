package com.agoda.clone.agoda.repository;

import java.util.Optional;

import com.agoda.clone.agoda.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
