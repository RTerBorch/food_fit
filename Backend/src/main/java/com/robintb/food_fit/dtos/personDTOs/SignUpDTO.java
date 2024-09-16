package com.robintb.food_fit.dtos.personDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignUpDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private char[] password;
    private String email;

    public SignUpDTO(String firstName, String lastName, String username, char[] password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}