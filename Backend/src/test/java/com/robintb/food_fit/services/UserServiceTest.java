package com.robintb.food_fit.services;

import com.robintb.food_fit.dtos.personDTO.CredentialsDTO;
import com.robintb.food_fit.dtos.personDTO.SignUpDTO;
import com.robintb.food_fit.dtos.personDTO.UserDTO;
import com.robintb.food_fit.mappers.UserMapper;
import com.robintb.food_fit.models.User;
import com.robintb.food_fit.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddPerson() {
        String username = "testUser";
        String firstName = "Test firstname";
        String lastName = "Test lastname";
        String password = "password123";
        String email = "test@example.com";

        SignUpDTO userToAdd = new SignUpDTO(firstName, lastName, username, password.toCharArray(), email);

        User newUser = new User(username, firstName, lastName, password, email);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User registeredUser = userService.register(userToAdd);
        verify(userRepository, times(1)).save(newUser);
        assertNotNull(registeredUser);
        assertEquals(newUser, registeredUser);
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
