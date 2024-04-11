package com.hotel.hotelManagment.repository;

import com.hotel.hotelManagment.Enums.UserRole;
import com.hotel.hotelManagment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {
                                        //the repo is responsible for objects with User type
                                        //second arg :the identifier of the entity
    Optional<User> findFirstByEmail(String email);
    //research method in order to find a user using his email
    Optional<User> findFirstByUserRole(UserRole userRole);
}
