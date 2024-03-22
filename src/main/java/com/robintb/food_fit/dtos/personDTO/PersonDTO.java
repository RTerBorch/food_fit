package com.robintb.food_fit.dtos.personDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private Long id;
    private String name;
    private String email;
    private double weight;
    private int age;

    public PersonDTO(String name, String email, double weight, int age) {
        this.name = name;
        this.email = email;
        this.weight = weight;
        this.age = age;
    }

}

