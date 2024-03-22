package com.robintb.food_fit.services;

import com.robintb.food_fit.dtos.personDTO.LoginCredentialsDTO;
import com.robintb.food_fit.dtos.personDTO.PersonDTO;
import com.robintb.food_fit.models.Person;
import com.robintb.food_fit.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    public void testAddPerson() {
        String username = "testUser";
        String name = "Test Name";
        String password = "password123";
        String email = "test@example.com";
        double weight = 70.5;
        int age = 30;
        Person personToAdd = new Person(username, name, password, email, weight, age);
        when(personRepository.save(any(Person.class))).thenReturn(personToAdd);

        Person addedPerson = personService.addPerson(username, name, password, email, weight, age);
        verify(personRepository, times(1)).save(any(Person.class));
        assertSame(personToAdd, addedPerson);
    }

    @Test
    void deletePersonTest() {
        long personId = 1L;
        com.robintb.food_fit.models.Person person = new com.robintb.food_fit.models.Person("testUser", "Test Name", "password123", "test@example.com", 70.5, 30);

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        personService.deletePerson(personId);
        verify(personRepository, times(1)).delete(person);
    }

    @Test
    void deleteNonExistingPersonTest() {
        long nonExistingPersonId = 2L;
        when(personRepository.findById(nonExistingPersonId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> personService.deletePerson(nonExistingPersonId));
        verify(personRepository, never()).delete(any());
    }

    @Test
    void modifyPersonTest() {

        long personId = 1L;
        String newName = "New Name";
        String newEmail = "new@example.com";
        double newWeight = 75.0;
        int newAge = 35;
        PersonDTO personDTO = new PersonDTO(personId, newName, newEmail, newWeight, newAge);

        Person existingPerson = new Person(personId,"oldUsername", "oldName", "OldPass", "old@example.com", 70.0, 30);
        when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));

        personService.modifyPerson(personDTO);

        assertEquals(newName, existingPerson.getName());
        assertEquals(newEmail, existingPerson.getEmail());
        assertEquals(newWeight, existingPerson.getWeight());
        assertEquals(newAge, existingPerson.getAge());
        verify(personRepository, times(1)).save(existingPerson);

    }

    @Test
    void updateLoginCredentialsTest() {
        long personId = 1L;
        String newUsername = "newUsername";
        String newPassword = "newPassword";
        LoginCredentialsDTO loginCredentialsDTO = new LoginCredentialsDTO(personId, newUsername, newPassword);

        Person existingPerson = new Person(personId, "oldUsername", "oldName", "oldPassword", "old@example.com", 70.0, 30);
        when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));

        personService.updateLoginCredentials(loginCredentialsDTO);

        assertEquals(newUsername, existingPerson.getUsername());
        assertEquals(newPassword, existingPerson.getPassword());

        if (existingPerson.getUsername().equals(newUsername) || existingPerson.getPassword().equals(newPassword)) {
            verify(personRepository, times(1)).save(existingPerson);
        } else {
            verify(personRepository, never()).save(existingPerson);
        }



    }

}
