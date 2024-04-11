package com.hotel.hotelManagment.dto;

import com.hotel.hotelManagment.entity.Room;
import lombok.Data;

import java.util.List;

@Data
public class RoomDto {

    private Long id;
    private String roomNumber;
    private Room.RoomType roomType;
    private int bedCount;
    private double price;
    private String description;
}
