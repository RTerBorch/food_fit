package com.robintb.food_fit.services;

import com.robintb.food_fit.dtos.personDTOs.CredentialsDTO;
import com.robintb.food_fit.dtos.personDTOs.SignUpDTO;
import com.robintb.food_fit.dtos.personDTOs.UserDTO;
import com.robintb.food_fit.exceptions.AppException;
import com.robintb.food_fit.mappers.UserMapper;
import com.robintb.food_fit.models.User;
import com.robintb.food_fit.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO findByLogin(String username){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        return userMapper.toUserDTO(user);
    }
    public UserDTO login(CredentialsDTO credentialsDTO) {
        User user = userRepository.findByUsername(credentialsDTO.getUsername())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.getPassword()), user.getPassword())) {
            return userMapper.toUserDTO(user);
        }

        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDTO register(SignUpDTO signUpDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(signUpDTO.getUsername());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User newUser = userMapper.signUpToUser(signUpDTO);

        if (newUser == null) {
            throw new AppException("Error mapping SignUpDTO to User", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        newUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.getPassword())));

        userRepository.save(newUser);

        return userMapper.toUserDTO(newUser);
    }



    public void deleteUser(Long personId) {
        Optional<User> personOptional = userRepository.findById(personId);
        if (personOptional.isPresent()) {
            userRepository.delete(personOptional.get());
        } else {
            throw new IllegalArgumentException("Person with ID " + personId + " not found.");
        }
    }

    public void updateLoginCredentials(CredentialsDTO credentialsDTO) {

        User userToLoginCredentials = userRepository.findByUsername(credentialsDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));

        boolean changesFound = false;

        if (!Arrays.equals(userToLoginCredentials.getPassword().toCharArray(), credentialsDTO.getPassword())) {
            userToLoginCredentials.setPassword(String.valueOf(credentialsDTO.getPassword()));
            changesFound = true;
        }
        if (!userToLoginCredentials.getUsername().equals(credentialsDTO.getUsername())) {
            userToLoginCredentials.setUsername(credentialsDTO.getUsername());
            changesFound = true;
        }

        if (changesFound) {
            userRepository.save(userToLoginCredentials);
        }

    }

    public void modifyPerson(UserDTO userDTO) {
        User userToModify = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));

        boolean changesFound = false;

        if (!userToModify.getFirstName().equals(userDTO.getFirstName())) {
            userToModify.setFirstName(userDTO.getFirstName());
            changesFound = true;
        }

        if (!userToModify.getLastName().equals(userDTO.getLastName())) {
            userToModify.setLastName(userDTO.getLastName());
            changesFound = true;
        }
        if (!userToModify.getEmail().equals(userDTO.getEmail())) {
            userToModify.setEmail(userDTO.getEmail());
            changesFound = true;
        }

        if (!userToModify.getUsername().equals(userDTO.getUsername())) {
            userToModify.setUsername(userDTO.getUsername());
            changesFound = true;
        }

        if (changesFound) {
            userRepository.save(userToModify);
        }

    }

}