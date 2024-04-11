package com.hotel.hotelManagment.Services.Auth;

import com.hotel.hotelManagment.dto.SignUpRequest;
import com.hotel.hotelManagment.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    public UserDto createUser(SignUpRequest signUpRequest);
}
