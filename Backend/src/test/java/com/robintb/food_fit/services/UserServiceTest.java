package com.robintb.food_fit.services;

import com.robintb.food_fit.dtos.personDTOs.CredentialsDTO;
import com.robintb.food_fit.dtos.personDTOs.SignUpDTO;
import com.robintb.food_fit.dtos.personDTOs.UserDTO;
import com.robintb.food_fit.exceptions.AppException;
import com.robintb.food_fit.mappers.UserMapper;
import com.robintb.food_fit.models.User;
import com.robintb.food_fit.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testFindByLogin() {
        String username = "testUser";
        User user = new User(username, "Test firstname", "Test lastname", "password123", "test@example.com");
        UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername());

        // Mocking the UserRepository and UserMapper
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        // Call the service method
        UserDTO foundUser = userService.findByLogin(username);

        // Assertions
        assertNotNull(foundUser);
        assertEquals(userDTO.getId(), foundUser.getId());
        assertEquals(userDTO.getUsername(), foundUser.getUsername());
        assertEquals(userDTO.getFirstName(), foundUser.getFirstName());
        assertEquals(userDTO.getLastName(), foundUser.getLastName());
        assertEquals(userDTO.getEmail(), foundUser.getEmail());

        // Verify interactions
        verify(userRepository, times(1)).findByUsername(username);
        verify(userMapper, times(1)).toUserDTO(user);
    }

    @Test
    public void testFindByLogin_UserNotFound() {
        String username = "unknownUser";

        // Mocking the UserRepository to return empty
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Call the service method and assert the exception
        AppException exception = assertThrows(AppException.class, () -> userService.findByLogin(username));
        assertEquals("Unknown user", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        // Verify interactions
        verify(userRepository, times(1)).findByUsername(username);
        verify(userMapper, never()).toUserDTO(any());
    }

    @Test
    public void testAddPerson() {
        String username = "testUser";
        String firstName = "Test firstname";
        String lastName = "Test lastname";
        String password = "password123";
        String encodedPassword = "encodedPassword123";
        String email = "test@example.com";

        SignUpDTO userToAdd = new SignUpDTO(firstName, lastName, username, password.toCharArray(), email);
        User newUser = new User(username, firstName, lastName, encodedPassword, email);
        UserDTO userDTO = new UserDTO(newUser.getId(), firstName, lastName, email, username);


        when(userMapper.signUpToUser(userToAdd)).thenReturn(newUser);
        when(userMapper.toUserDTO(newUser)).thenReturn(userDTO);

        when(passwordEncoder.encode(any(CharBuffer.class))).thenReturn(encodedPassword);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        UserDTO registeredUser = userService.register(userToAdd);

        assertNotNull(registeredUser);
        assertEquals(userDTO.getId(), registeredUser.getId());
        assertEquals(userDTO.getUsername(), registeredUser.getUsername());
        assertEquals(userDTO.getLastName(), registeredUser.getLastName());
        assertEquals(userDTO.getFirstName(), registeredUser.getFirstName());
        assertEquals(userDTO.getEmail(), registeredUser.getEmail());
        verify(passwordEncoder).encode(CharBuffer.wrap(password.toCharArray()));
        verify(userRepository, times(1)).save(newUser);
    }


    @Test
    void deletePersonTest() {
        long personId = 1L;
        User user = new User("testUser", "Test firstname","Test lastname", "password123", "test@example.com");

        when(userRepository.findById(personId)).thenReturn(Optional.of(user));
        userService.deleteUser(personId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteNonExistingPersonTest() {
        long nonExistingPersonId = 2L;
        when(userRepository.findById(nonExistingPersonId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(nonExistingPersonId));
        verify(userRepository, never()).delete(any());
    }

    @Test
    void modifyPersonTest() {

        long personId = 1L;
        String username = "newUsername";
        String newFirstName = "new firstname";
        String newLastName = "new lastname";
        String newEmail = "new@example.com";
        double newWeight = 75.0;
        int newAge = 35;
        UserDTO userDTO = new UserDTO(personId, newFirstName,newLastName, newEmail,username);
        User existingUser = new User(personId,"oldUsername", "oldFirstName","oldLastName", "OldPass", "old@example.com");
        when(userRepository.findById(personId)).thenReturn(Optional.of(existingUser));

        userService.modifyPerson(userDTO);

        assertEquals(userDTO.getFirstName(), existingUser.getFirstName());
        assertEquals(userDTO.getLastName(), existingUser.getLastName());
        assertEquals(userDTO.getUsername(), existingUser.getUsername());
        assertEquals(userDTO.getEmail(), existingUser.getEmail());
        verify(userRepository, times(1)).save(existingUser);

    }

    @Test
    void updateLoginCredentialsTest() {
        long personId = 1L;

        CredentialsDTO credentialsDTO = new CredentialsDTO("newUsername", "newPassword".toCharArray());
        User existingUser = new User(personId, "oldUsername", "oldFirstName","oldLastName", "oldPassword", "old@example.com");

        when(userRepository.findByUsername(credentialsDTO.getUsername())).thenReturn(Optional.of(existingUser));

        userService.updateLoginCredentials(credentialsDTO);

        assertEquals(credentialsDTO.getUsername(), existingUser.getUsername());
        assertArrayEquals(existingUser.getPassword().toCharArray(), credentialsDTO.getPassword());

        if (existingUser.getUsername().equals(credentialsDTO.getUsername()) || Arrays.equals(existingUser.getPassword().toCharArray(), credentialsDTO.getPassword())) {
            verify(userRepository, times(1)).save(existingUser);
        } else {
            verify(userRepository, never()).save(existingUser);
        }
    }


}
