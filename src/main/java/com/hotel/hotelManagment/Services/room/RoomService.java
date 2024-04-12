package com.hotel.hotelManagment.Services.room;

import com.hotel.hotelManagment.dto.RoomDto;
import com.hotel.hotelManagment.entity.Room;
import com.hotel.hotelManagment.repository.RoomRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room createRoom(Room room) {
        if(roomRepository.findRoomByRoomNumber(room.getRoomNumber()).isEmpty()){
            return roomRepository.save(room);
        }
        else
            throw new EntityExistsException("User Already exists ! " + room.getRoomNumber());
    }

    public List<Room> getAllRooms() {
        return (List<Room>) roomRepository.findAll();
    }

    public List<Room> findRoomsByRoomType(Room.RoomType roomType) {
        return roomRepository.findByRoomType(roomType);
    }

    public List<Room> findRoomsByPriceRange(double minPrice, double maxPrice) {
        return roomRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Room> findRoomByPriceRangeAndType(double minPrice, double maxPrice, Room.RoomType roomType)
    {
        List<Room> ByPriceListedRooms = roomRepository.findByPriceBetween(minPrice, maxPrice);
        List<Room> ByTypeListedRooms = roomRepository.findByRoomType(roomType);
        List<Room> CombinedRooms = new ArrayList<>();
        for (Room room : ByPriceListedRooms) {
            if (ByTypeListedRooms.contains(room)) {
                CombinedRooms.add(room);
            }
        }
        return CombinedRooms;
    }
     public List<RoomDto> convertRoomsToDtos(List<Room> rooms)
     {
         List<RoomDto> roomDtos = new ArrayList<>();
         for (Room room : rooms) {
             RoomDto roomDto = room.getRoomDto();
             roomDtos.add(roomDto);
         }
        return roomDtos;
     }
    public Room updateRoom(Room room) {
        // Validation (optional)
        if (room.getId() == null || !roomRepository.existsById(room.getId())) {
            throw new IllegalArgumentException("Invalid room ID for update");
        }
        return roomRepository.save(room);
    }

    public void deleteRoom(String roomNumber) {
        Optional<Room> room = roomRepository.findRoomByRoomNumber(roomNumber);
        if (room.isEmpty()) {
            throw new IllegalArgumentException("Room with Number " + roomNumber + " not found");
        }
        roomRepository.deleteById(room.get().getId());
    }

    public Optional<Room> findById(Long roomId) {
        if(!roomRepository.existsById(roomId))
        {
            throw new IllegalArgumentException("Invalid room ID");
        }
        return roomRepository.findRoomById(roomId);
    }
    public Optional<Room> findByNumber(String roomNumber) {
        Optional<Room> room = roomRepository.findRoomByRoomNumber(roomNumber);
        if(room.isEmpty())
        {
            throw new IllegalArgumentException("Invalid room ID");
        }
        return room;
    }

    public List<Room> findRoomByType(Room.RoomType roomType) {
        List<Room> room = roomRepository.findByRoomType(roomType);
        if(room.isEmpty())
        {
            throw new IllegalArgumentException("Invalid room ID");
        }
        return room;
    }
}