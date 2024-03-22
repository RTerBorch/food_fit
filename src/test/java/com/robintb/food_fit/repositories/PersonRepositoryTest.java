package com.robintb.food_fit.repositories;

import com.robintb.food_fit.models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    private Person createPerson(String username, String name, String password, String email, double weight, int age) {
        Person person = new Person(username, name, password, email, weight, age);
        person.setUsername(username);
        person.setName(name);
        person.setEmail(email);
        person.setWeight(weight);
        person.setAge(age);
        return personRepository.save(person);
    }

    @AfterEach
    public void tearDown() {
        personRepository.deleteAll();
    }


    @Test
    public void testSavePerson(){
        Person savedPerson = createPerson("user","John", "password","john@example.com", 75.5, 30);

        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getUsername()).isEqualTo("user");
        assertThat(savedPerson.getName()).isEqualTo("John");
        assertThat(savedPerson.getEmail()).isEqualTo("john@example.com");
        assertThat(savedPerson.getWeight()).isEqualTo(75.5);
        assertThat(savedPerson.getAge()).isEqualTo(30);

    }

    @Test
    public void testFindByUsername(){
        createPerson("user","John", "password", "john@example.com", 75.5, 30);

        Person foundPerson = personRepository.findByUsername("user");

        assertThat(foundPerson.getUsername()).isEqualTo("user");
        assertThat(foundPerson.getName()).isEqualTo("John");
        assertThat(foundPerson.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    public void testFindByEmail() {
        createPerson("user","John","password", "john@example.com", 75.5, 30);

        Person foundPerson = personRepository.findByEmail("john@example.com");

        assertThat(foundPerson).isNotNull();
        assertThat(foundPerson.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    public void testDeletePerson() {
        Person savedPerson = createPerson("user","John", "password", "john@example.com", 75.5, 30);

        personRepository.delete(savedPerson);

        Person deletedPerson = personRepository.findByEmail("john@example.com");
        assertThat(deletedPerson).isNull();
    }

}
