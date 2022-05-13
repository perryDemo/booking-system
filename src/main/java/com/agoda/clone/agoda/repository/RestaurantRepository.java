package com.agoda.clone.agoda.repository;

import com.agoda.clone.agoda.model.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Integer>{
    
}
