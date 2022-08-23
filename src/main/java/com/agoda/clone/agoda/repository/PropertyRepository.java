package com.agoda.clone.agoda.repository;

import com.agoda.clone.agoda.model.Property;
import com.agoda.clone.agoda.model.Room;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Integer>{
    List<Property> findByDeletedat(Instant deletedat);
    Optional<Property> findByIDAndDeletedat(int id,Instant deletedat);
    List<Property> findByRoomIn(List<Room> room);
    @Query(value ="SELECT * "
        +   "FROM Property WHERE DeletedBy = :deletedby and (Area = :area or City = :city)", nativeQuery = true)
    List<Property> findByDeletedbyAndAreaOrCity(String deletedby, String area, String city);
    Property findByRoom(Room room);
    @Query(value ="SELECT * "
        +   "FROM Property WHERE DeletedBy = :deletedby and id = :id and (Area = :area or City = :city)", nativeQuery = true)
    Property findByDeletedbyAndIDAndAreaOrCity(String deletedby, int id, String area, String city);

    Page<Property> findByIDIn(List<Integer> ID, Pageable pageable);

}
