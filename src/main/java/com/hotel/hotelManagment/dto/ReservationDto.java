package com.hotel.hotelManagment.dto;

import com.hotel.hotelManagment.entity.Reservation;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationDto {

    private Long id;
    private Long id_user;
    private Long id_room;
    private Date startDate;
    private Date endDate;
    private Long priceReservation;
    private Reservation.Status status;
}
