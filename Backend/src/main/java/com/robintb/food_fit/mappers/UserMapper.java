package com.robintb.food_fit.mappers;

import com.robintb.food_fit.dtos.personDTO.SignUpDTO;
import com.robintb.food_fit.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User signUpToUser(SignUpDTO signUpDTO) {
        return User.builder()
                .username(signUpDTO.getUsername())
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .email(signUpDTO.getEmail())
                .build();
    }
}
