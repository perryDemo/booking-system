package com.agoda.clone.agoda.repository;

import java.util.Optional;

import com.agoda.clone.agoda.model.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository  extends JpaRepository<Room, Integer>{
    Optional<Room> findById(int id);
}
