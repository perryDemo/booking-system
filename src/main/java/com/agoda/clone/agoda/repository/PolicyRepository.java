package com.agoda.clone.agoda.repository;

import com.agoda.clone.agoda.model.Policy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Integer>{
        
}
