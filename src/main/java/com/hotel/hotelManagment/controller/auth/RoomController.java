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

    //creating rooms
    @PostMapping("/createroom")
    public ResponseEntity<?> createRoom(@RequestBody RoomDto roomDto) {
        Room room = new Room(roomDto);
        try {
            Room createdRoom = roomService.createRoom(room);
            RoomDto createdRoomDto = createdRoom.getRoomDto();
            return new ResponseEntity<>(createdRoomDto, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //returning all rooms of the hotel (to be used for exploation)
    @GetMapping("/showallrooms")
    public ResponseEntity<?> getAllRooms() {
        try {
            List<Room> allRooms = roomService.getAllRooms();
            List<RoomDto> roomDtos = roomService.convertRoomsToDtos(allRooms);
            return new ResponseEntity<>(roomDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //returning room details only by giving the room id
    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomById(@PathVariable Long roomId) {
        Optional<Room> room = roomService.findById(roomId);
        if (room.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        RoomDto roomDto = room.get().getRoomDto();
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{roomNumber}")
    public ResponseEntity<?> deleteRoom(@PathVariable String roomNumber)
    {
        Optional<Room> room = roomService.findByNumber(roomNumber);
        RoomDto roomDto = room.get().getRoomDto();
        roomService.deleteRoom(roomNumber);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    //the search functionality takes 3 parameters that can also take a null return,
    //trying to satisfy all the search-parameters specifications by the users (prixMax,PrixMin,Type de la chambre)
    @GetMapping("/search")
    public ResponseEntity<?> searchRooms(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Room.RoomType roomType)
    {
        List<Room> filteredRooms = new ArrayList<>();
        if(minPrice!=null && maxPrice!=null && roomType!=null)
        {
            filteredRooms = roomService.findRoomByPriceRangeAndType(minPrice, maxPrice, roomType);
        }
        else if(minPrice == null && maxPrice == null && roomType == null)
        {
            filteredRooms = roomService.findRoomsByPriceRange(0.0, 99999.0);
        }
        else
        if(maxPrice!=null && roomType!=null)
        {
            minPrice = 0.0;
            filteredRooms = roomService.findRoomByPriceRangeAndType(minPrice, maxPrice, roomType);
        }
        else
        if(minPrice != null && maxPrice != null)
        {
            filteredRooms = roomService.findRoomsByPriceRange(minPrice, maxPrice);
        }
        else
        if(minPrice != null && roomType != null)
        {
            maxPrice = 99999.0; //prix hautement incomparable avec les autres.
            filteredRooms = roomService.findRoomByPriceRangeAndType(minPrice, maxPrice, roomType);
        }
        else
        if(roomType!=null) {
            filteredRooms = roomService.findRoomByType(roomType);
        }
        List<RoomDto> roomDtos = roomService.convertRoomsToDtos(filteredRooms);
        return new ResponseEntity<>(roomDtos, HttpStatus.OK);
    }
   /* @PutMapping("/editRoomDetails")
    public ResponseEntity<?> editRoomDetails(@RequestParam(required = true) Long id , @RequestBody RoomDto newDetails) {
        Optional<Room> room = roomService.findById(id);
        try {
            Room updatedRoom = roomService.updateRoom(room);
            RoomDto updatedRoomRoomDto = updatedRoom.getRoomDto();
            return new ResponseEntity<>(updatedRoomRoomDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    */
}
