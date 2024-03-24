package com.robintb.food_fit.dtos.personDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CredentialsDTO {
    private String username;
    private char[] password;
}
