package com.hotel.hotelManagment.repository;

import com.hotel.hotelManagment.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Long> {

    List<Room> findByRoomType(Room.RoomType roomType);
    List<Room> findByPriceBetween(double minPrice, double maxPrice);

    Optional<Room> findRoomById(Long roomId);

}
