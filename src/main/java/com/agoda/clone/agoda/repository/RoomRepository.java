package com.agoda.clone.agoda.repository;


import com.agoda.clone.agoda.model.Offer;
import com.agoda.clone.agoda.model.Property;
import com.agoda.clone.agoda.model.Room;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository  extends JpaRepository<Room, Integer>{
    Room findById(int id);
    List<Room> findByIDIn(Collection<Integer> ID);
    List<Room> findByPropertyIn(List<Property> property);
    List<Room> findByProperty(Property property);
    @Query(value = "select * from room where id in :id group by propertyid", nativeQuery = true )
    List<Room> findByIDInGroupByPropertyid(Collection<Integer> id);
    List<Room> findByOfferIn(List<Offer> offer);
    Room findByOffer(Optional<Offer> offer);
}
