package com.robintb.food_fit.repositories;

import com.robintb.food_fit.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String mail);
    Person findByUsername(String username);
}