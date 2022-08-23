package com.agoda.clone.agoda.repository;



import com.agoda.clone.agoda.model.HotelCount;
import com.agoda.clone.agoda.model.Offer;
import com.agoda.clone.agoda.model.Room;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Integer>{
    Offer findById(int id);

    @Query("SELECT new com.agoda.clone.agoda.model.HotelCount(o.room, COUNT(o.room))"
        +   "FROM Offer AS o WHERE o.guest >= :guest and quantity >= :quantity and checkindate BETWEEN :start AND :end GROUP BY o.room ")
    List<HotelCount> countOfferById(int guest, int quantity, Date start, Date end);

    List<Offer> findByRoomInAndCheckindateBetween(List<Room> room, Date checkindateStart, Date checkindateEnd); 

    List<Offer> findByGuestGreaterThanEqual(int guest); 

    List<Offer> findByIDIn(List<Integer> ID);
}
