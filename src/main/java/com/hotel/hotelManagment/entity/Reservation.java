package com.hotel.hotelManagment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false) // Foreign key for User
    private User user; //user id is the idetifier for user

    @ManyToOne
    @JoinColumn(nullable = false) // Foreign key for Room
    private Room room; //room id is the idetifier for room

    private Date startDate;
    private Date endDate;
    private double reservationPrice; //calculared by this formula : (EndDate - startDate) * Room.price

    @Enumerated(EnumType.STRING)
    private Reservation.Status roomStatus;//For front a better user experience(letting the user know about the status of his reservation)
    // (all my tests are going to be using Confirmed only)
    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELLED
    };
}