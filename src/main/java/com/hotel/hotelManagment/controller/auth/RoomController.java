package com.hotel.hotelManagment.controller.auth;

import com.hotel.hotelManagment.Services.room.RoomService;
import com.hotel.hotelManagment.dto.RoomDto;
import com.hotel.hotelManagment.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;


    @PostMapping("/createroom")
    public ResponseEntity<?> createRoom(@RequestBody RoomDto roomDto) {
        System.out.println("!!!!! Room dto = " + roomDto);
        Room room = new Room(roomDto);
        try {
            Room createdRoom = roomService.createRoom(room);
            RoomDto createdRoomDto = roomService.ConvertRoomToDto(createdRoom);
            return new ResponseEntity<>(createdRoomDto, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        List<Room> allRooms = roomService.getAllRooms();
        List<RoomDto> roomDtos = roomService.convertRoomsToDtos(allRooms);
        return ResponseEntity.ok(roomDtos);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId) {
        Optional<Room> room = roomService.findById(roomId); // Assuming a findById method in RoomService
        if (room.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RoomDto roomDto = new RoomDto(); // Map Room to DTO object (implement mapping logic)
        return ResponseEntity.ok(roomDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RoomDto>> searchRooms(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Room.RoomType roomType)
    {
        List<Room> filteredRooms = roomService.findRoomByPriceRangeAndType(minPrice, maxPrice, roomType);
        List<RoomDto> roomDtos = roomService.convertRoomsToDtos(filteredRooms);
        return ResponseEntity.ok(roomDtos);
    }
}
