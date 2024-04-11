package com.hotel.hotelManagment.dto;

import com.hotel.hotelManagment.Enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private UserRole userRole;

}
