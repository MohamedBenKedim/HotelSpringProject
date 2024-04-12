package com.hotel.hotelManagment.Services.Reservation;

import com.hotel.hotelManagment.dto.ReservationRequest;
import com.hotel.hotelManagment.dto.ReservationResponse;
import com.hotel.hotelManagment.entity.Reservation;
import com.hotel.hotelManagment.entity.Room;
import com.hotel.hotelManagment.entity.User;
import com.hotel.hotelManagment.repository.ReservationRepository;
import com.hotel.hotelManagment.repository.RoomRepository;
import com.hotel.hotelManagment.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ReservationResponse transformReserverationIntoResponse(Reservation reservation)
    {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setPrice(reservation.getReservationPrice());
        reservationResponse.setRoomType(reservation.getRoom().getRoomType());
        reservationResponse.setBedCount(reservation.getRoom().getBedCount());
        reservationResponse.setStartDate(reservation.getStartDate());
        reservationResponse.setEndDate(reservation.getEndDate());
        return reservationResponse;
    }
    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        //calculating the period of time of the reservation
        long diffInMilliSeconds = reservationRequest.getEndDate().getTime() - reservationRequest.getStartDate().getTime();
        long daysBetween = diffInMilliSeconds / (24 * 60 * 60 * 1000);
        Optional<User> user = userRepository.findById(reservationRequest.getId_user());

        //getting rooms that has some specific properties according to the user
        List<Room> rooms = roomRepository.findRoomByRoomTypeAndBedCount(
                reservationRequest.getRoomType(), reservationRequest.getBedCount()
        );

        //looking if one of the desired rooms is empty, so we put it in reserve
        if (rooms.isEmpty()) {
            throw new EntityNotFoundException("Room types is not availible");
        } else {
            for (Room room : rooms) {
                //for each room we look if it satisfies the reservation conditions
                Optional<Reservation> ReservationRoom = reservationRepository
                        .findReservationByRoomIdAndEndDateBetweenAndStartDateBetween(
                                room.getId(),
                                reservationRequest.getStartDate(),
                                reservationRequest.getEndDate(),
                                reservationRequest.getStartDate(),
                                reservationRequest.getEndDate()
                        );
                System.out.println("************RESERVATION****************");
                //if we find a room with no reservation and that matches the conditions we do the reservation

                if (ReservationRoom.isEmpty()) {
                    //setting up the reservation details
                    Reservation reservation = new Reservation();
                    reservation.setReservationPrice(daysBetween * room.getPrice());
                    reservation.setRoom(room);
                    reservation.setStartDate(reservationRequest.getStartDate());
                    reservation.setEndDate(reservationRequest.getEndDate());
                    reservation.setUser(user.get());
                    reservation.setRoomStatus(Reservation.Status.CONFIRMED);

                    //setting up the reservation Response details
                    ReservationResponse reservationResponse = transformReserverationIntoResponse(reservation);
                    reservationResponse.setEmail(user.get().getEmail());
                    reservationResponse.setRoomNumber(room.getRoomNumber());
                    reservationRepository.save(reservation);
                    return reservationResponse;
                }
        }
    }
        return null;
    }
}
