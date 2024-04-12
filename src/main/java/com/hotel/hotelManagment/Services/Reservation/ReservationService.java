package com.hotel.hotelManagment.Services.Reservation;

import com.hotel.hotelManagment.dto.ReservationRequest;
import com.hotel.hotelManagment.dto.ReservationResponse;
import com.hotel.hotelManagment.entity.Reservation;
import org.springframework.stereotype.Service;


@Service
public interface ReservationService {
    public ReservationResponse createReservation(ReservationRequest reservationRequest);

}
