package com.robintb.food_fit.controllers;

import com.robintb.food_fit.dtos.personDTO.CredentialsDTO;
import com.robintb.food_fit.dtos.personDTO.UserDTO;
import com.robintb.food_fit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> getPerson(@RequestBody CredentialsDTO credentialsDTO) {

       // UserDTO user = userService.login(credentialsDTO);



        return null;
    }


}
