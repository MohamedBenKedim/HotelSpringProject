package com.hotel.hotelManagment.controller.auth;

import com.hotel.hotelManagment.Services.Auth.AuthService;
import com.hotel.hotelManagment.Services.jwt.UserService;
import com.hotel.hotelManagment.dto.AuthenticationRequest;
import com.hotel.hotelManagment.dto.AuthenticationResponse;
import com.hotel.hotelManagment.dto.SignUpRequest;
import com.hotel.hotelManagment.dto.UserDto;
import com.hotel.hotelManagment.entity.User;
import com.hotel.hotelManagment.repository.UserRepository;
import com.hotel.hotelManagment.util.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor //inject the final objects in the controller(a constractor initiates instances of each final)
public class AuthController {

    private final AuthService authService ;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignUpRequest signUpRequest)
    {
        try
        {
            UserDto createdUser = authService.createUser(signUpRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } catch (EntityExistsException entityExistsException)
        {
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);

        } catch (Exception e)
        {
            return new ResponseEntity<>("User not created, Try again later !", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword())
            ); //an AuthenticationProvider attempts to verify the credentials on jpa repo having in args an @Entity object
        }
        catch(BadCredentialsException e)
        {
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionaluser =userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(optionaluser.isPresent())
        {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionaluser.get().getUserRole());
            authenticationResponse.setUserId(optionaluser.get().getId());
        }
        return authenticationResponse;
    }
}
