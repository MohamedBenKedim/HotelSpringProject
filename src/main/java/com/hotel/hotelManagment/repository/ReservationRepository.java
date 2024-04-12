package com.hotel.hotelManagment.repository;


import com.hotel.hotelManagment.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findReservationByEndDateBeforeAndStartDateAfterAndRoomId(Date EndDate, Date startDate, Long id_room);
    Optional<Reservation> findReservationByRoomIdAndEndDateBetweenAndStartDateBetween(Long id_room, Date endDate, Date endDate2, Date startDate, Date startDate2);
}
