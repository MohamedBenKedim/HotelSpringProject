package com.hotel.hotelManagment.dto;

import com.hotel.hotelManagment.entity.Room;
import lombok.Data;

import java.util.Date;


@Data
public class ReservationResponse {

    private Room.RoomType roomType;
    private int bedCount;
    private Date startDate;
    private Date endDate;
    private double price;
    private String email;
    private String roomNumber;
}
