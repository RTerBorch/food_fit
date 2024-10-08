package com.robintb.food_fit.controllers;

import com.robintb.food_fit.config.UserAuthenticationProvider;
import com.robintb.food_fit.dtos.personDTOs.CredentialsDTO;
import com.robintb.food_fit.dtos.personDTOs.SignUpDTO;
import com.robintb.food_fit.dtos.personDTOs.UserDTO;
import com.robintb.food_fit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> getPerson(@RequestBody CredentialsDTO credentialsDTO) {

        UserDTO user = userService.login(credentialsDTO);
        user.setToken(userAuthenticationProvider.createToken(user.getUsername()));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody SignUpDTO signUpDTO) {
        UserDTO user = userService.register(signUpDTO);
        user.setToken(userAuthenticationProvider.createToken(user.getUsername()));
        return ResponseEntity.created(URI.create("/users/" + user.getId()))
                .body(user);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<String>> messages(){
        return ResponseEntity.ok(Arrays.asList("first", "second"));
    }



}
