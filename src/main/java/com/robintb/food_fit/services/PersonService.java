package com.robintb.food_fit.services;

import com.robintb.food_fit.dtos.personDTO.LoginCredentialsDTO;
import com.robintb.food_fit.dtos.personDTO.PersonDTO;
import com.robintb.food_fit.models.Person;
import com.robintb.food_fit.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PersonService {

    private static final Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person addPerson(String username, String name, String password, String email, double weight, int age) {
        try {
            Person person = new Person(username, name, password, email, weight, age);
            return personRepository.save(person);

        } catch (DataIntegrityViolationException e) {
            logger.severe("Error occurred while adding person: {}" + e.getMessage());
            throw new IllegalArgumentException("Unable to add person. Data integrity violation occurred.");
        } catch (DataAccessException e) {
            logger.severe("Error occurred while adding person: {}" + e.getMessage());
            throw new RuntimeException("Unable to add person due to a database access issue.");
        }
    }

    public void deletePerson(Long personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isPresent()) {
            personRepository.delete(personOptional.get());
        } else {
            throw new IllegalArgumentException("Person with ID " + personId + " not found.");
        }
    }


    public void updateLoginCredentials(LoginCredentialsDTO updateLoginDTO) {

        Person personToLoginCredentials = personRepository.findById(updateLoginDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));

        boolean changesFound = false;

        if (!personToLoginCredentials.getPassword().equals(updateLoginDTO.getNewPassword())) {
            personToLoginCredentials.setPassword(updateLoginDTO.getNewPassword());
            changesFound = true;
        }
        if (!personToLoginCredentials.getUsername().equals(updateLoginDTO.getNewUsername())) {
            personToLoginCredentials.setUsername(updateLoginDTO.getNewUsername());
            changesFound = true;
        }

        if (changesFound){
            personRepository.save(personToLoginCredentials);
        }

    }

    public void modifyPerson(PersonDTO personDTO) {
        Person personToModify = personRepository.findById(personDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));

        boolean changesFound = false;

        if (!personToModify.getName().equals(personDTO.getName())) {
            personToModify.setName(personDTO.getName());
            changesFound = true;
        }
        if (!personToModify.getEmail().equals(personDTO.getEmail())) {
            personToModify.setEmail(personDTO.getEmail());
            changesFound = true;
        }

        if (personToModify.getWeight() != personDTO.getWeight()) {
            personToModify.setWeight(personDTO.getWeight());
            changesFound = true;
        }
        if (personToModify.getAge() != personDTO.getAge()) {
            personToModify.setAge(personDTO.getAge());
            changesFound = true;
        }

        if (changesFound){
          personRepository.save(personToModify);
        }

    }

}