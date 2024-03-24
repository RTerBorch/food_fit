package com.robintb.food_fit.repositories;

import com.robintb.food_fit.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User createPerson(String username, String firstName, String lastName, char[] password, String email) {
        User user = new User(username, firstName,lastName, password, email);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }


    @Test
    public void testSavePerson(){
        User savedUser = userRepository.save(new User("user","John","Doe", "password".toCharArray(),"john@example.com"));

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("user");
        assertThat(savedUser.getFirstName()).isEqualTo("John");
        assertThat(savedUser.getLastName()).isEqualTo("Doe");
        assertThat(savedUser.getEmail()).isEqualTo("john@example.com");

    }

    @Test
    public void testFindByUsername() {
        createPerson("user", "John", "Doe", "password".toCharArray(), "john@example.com");

        User foundUser = userRepository.findByUsername("user").orElseThrow(() -> new IllegalArgumentException("Person not found"));

        assertThat(foundUser.getUsername()).isEqualTo("user");
        assertThat(foundUser.getFirstName()).isEqualTo("John");
        assertThat(foundUser.getLastName()).isEqualTo("Doe");
        assertThat(foundUser.getEmail()).isEqualTo("john@example.com");
        assertThat(foundUser.getPassword()).isEqualTo("password".toCharArray());
    }

    @Test
    public void testDeletePerson() {
        User savedUser = createPerson("user","John","Doe", "password".toCharArray(), "john@example.com");

        // before delete
        Optional<User> beforeDeleteUser = userRepository.findByUsername("user");
        assertThat(beforeDeleteUser).isNotEmpty();

        // after delete
        userRepository.delete(savedUser);
        Optional<User> afterDeleteUser = userRepository.findByUsername("user");
        assertThat(afterDeleteUser).isEmpty();
    }

}
