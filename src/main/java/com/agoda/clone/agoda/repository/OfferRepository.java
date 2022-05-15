package com.agoda.clone.agoda.repository;



import com.agoda.clone.agoda.model.Offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Integer>{
    Offer findById(int id);
}
