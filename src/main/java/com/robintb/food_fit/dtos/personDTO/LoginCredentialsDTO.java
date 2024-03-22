package com.robintb.food_fit.dtos.personDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginCredentialsDTO {
    private Long id;
    private String newUsername;
    private String newPassword;
}
