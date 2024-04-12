package com.hotel.hotelManagment.repository;

import com.hotel.hotelManagment.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Long> {

    List<Room> findByRoomType(Room.RoomType roomType);

    List<Room> findRoomByRoomTypeAndBedCount(Room.RoomType roomType, int bedNumber);
    List<Room> findByPriceBetween(double minPrice, double maxPrice);

    Optional<Room> findRoomByRoomNumber(String roomNumber);
    Optional<Room> findRoomById(Long roomId);

   // void deleteById(String roomNumber);

}
