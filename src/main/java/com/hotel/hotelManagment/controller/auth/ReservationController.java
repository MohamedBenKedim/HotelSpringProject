package com.hotel.hotelManagment.controller.auth;

import com.hotel.hotelManagment.Services.Reservation.ReservationService;
import com.hotel.hotelManagment.dto.ReservationRequest;
import com.hotel.hotelManagment.dto.ReservationResponse;
import com.hotel.hotelManagment.entity.Reservation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.NullServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/createreservation")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);
            if(reservationResponse == null)
            {
                throw new EntityNotFoundException("Room is already Reserved !!");
            }else
            { return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED); }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
