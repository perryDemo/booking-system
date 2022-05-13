package com.agoda.clone.agoda.repository;

import com.agoda.clone.agoda.model.PropertyDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyDetailRepository extends JpaRepository<PropertyDetail, Integer>{
    
}
