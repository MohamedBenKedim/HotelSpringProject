package com.hotel.hotelManagment.dto;

import com.hotel.hotelManagment.entity.Reservation;
import com.hotel.hotelManagment.entity.Room;
import lombok.Data;

import java.util.Date;



@Data
public class ReservationRequest {

    private Long id_user;
    private Room.RoomType roomType;
    private int bedCount;
    private Date startDate;
    private Date endDate;
}
