package com.robintb.food_fit.controllers;

import com.robintb.food_fit.dtos.personDTO.CredentialsDTO;
import com.robintb.food_fit.dtos.personDTO.UserDTO;
import com.robintb.food_fit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/account")
@Controller
public class UserController {

    @Autowired
    UserService userService;

    // Get
    @PostMapping("/login")
    public ResponseEntity<UserDTO> getPerson(@RequestBody CredentialsDTO credentialsDTO) {



        return null;
    }


}
