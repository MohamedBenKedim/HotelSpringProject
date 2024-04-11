package com.hotel.hotelManagment.Services.Auth;

import com.hotel.hotelManagment.Enums.UserRole;
import com.hotel.hotelManagment.dto.SignUpRequest;
import com.hotel.hotelManagment.dto.UserDto;
import com.hotel.hotelManagment.entity.User;
import com.hotel.hotelManagment.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService{

    private final UserRepository userRepository;

    @PostConstruct  //execute after initiating all beans
    public void createAdminAccount() //here we verify if an admin account already exists or not
    {
        Optional<User> adminAccount = userRepository.findFirstByUserRole(UserRole.ADMIN);
        if(adminAccount.isEmpty())  //if there is no admin in the database , we create one
        {
            User user = new User(); // an admin is a user with an ADMIN role
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin")); //encryption of the password
            userRepository.save(user);
            System.out.println("Admin account created succesfully");

        }else
        {
            System.out.println("Admin account exists already !!");
        }
    }

    public UserDto createUser(SignUpRequest signUpRequest)
    {
        if(userRepository.findFirstByEmail(signUpRequest.getEmail()).isPresent()) //we can use isPresent because findFirstByEmail returns an Optional
        {            //repository is responsible for the database access using the defined functions in the repo class
            throw new EntityExistsException("User Already exists ! " +signUpRequest.getEmail());
        }
        User user = new User();
        user.setEmail(signUpRequest.getEmail()); //the signupRequest is a DTO containing the signup details
        user.setName(signUpRequest.getName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        User createUser = userRepository.save(user); //saving the user into the database : Spring Data JPA
        return createUser.getUserDto();
    }
}
