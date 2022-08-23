package com.agoda.clone.agoda.repository;

import com.agoda.clone.agoda.model.Booking;
import com.agoda.clone.agoda.model.Offer;
import com.agoda.clone.agoda.model.OfferCount;
import com.agoda.clone.agoda.model.User;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
    Page<Booking> findByUserAndCheckoutLessThan(User user, Date checkout, Pageable pageable);
    Page<Booking> findByUserAndCheckoutGreaterThanEqual(User user, Date checkout, Pageable pageable);
    List<Booking> findByOfferIn(List<Offer> offer);
    @Query("SELECT new com.agoda.clone.agoda.model.OfferCount(b.offer, b.checkin, COUNT(b.checkin), b.checkout, COUNT(b.checkout), SUM(b.quantity))"
    +   "FROM Booking AS b WHERE offerid IN :offerId AND checkin BETWEEN :start AND :end GROUP BY b.offer, b.checkin, b.checkout ")
    List<OfferCount> findOfferMatchRequire(List<Integer> offerId, Date start, Date end);

}
