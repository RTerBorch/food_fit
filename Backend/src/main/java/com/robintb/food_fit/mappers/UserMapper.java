package com.robintb.food_fit.mappers;

import com.robintb.food_fit.dtos.personDTOs.SignUpDTO;
import com.robintb.food_fit.dtos.personDTOs.UserDTO;
import com.robintb.food_fit.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User signUpToUser(SignUpDTO signUpDTO) {
        return User.builder()
                .username(signUpDTO.getUsername())
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .email(signUpDTO.getEmail())
                .build();
    }

    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
