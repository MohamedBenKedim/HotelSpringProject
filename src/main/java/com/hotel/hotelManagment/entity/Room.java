package com.hotel.hotelManagment.entity;
import com.hotel.hotelManagment.dto.RoomDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    private int bedCount;
    private double price;
    private String description;

    public enum RoomType {
        SINGLE,
        DOUBLE,
        SUITE
    }
    public Room(RoomDto roomDto) {
        this.roomNumber = roomDto.getRoomNumber();
        this.roomType = roomDto.getRoomType();
        this.bedCount = roomDto.getBedCount();
        this.price = roomDto.getPrice();
        this.description = roomDto.getDescription();
    }

}
