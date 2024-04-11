package com.hotel.hotelManagment.dto;

import com.hotel.hotelManagment.Enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private UserRole userRole;

}
