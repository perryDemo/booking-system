package com.agoda.clone.agoda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agoda.clone.agoda.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
    
}
