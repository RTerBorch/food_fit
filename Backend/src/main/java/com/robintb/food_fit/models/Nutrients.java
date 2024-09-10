package com.robintb.food_fit.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Nutrients {

    @Id
    private Long id;
}
