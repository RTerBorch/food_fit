package com.robintb.food_fit.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class FoodItem {

    @Id
    private Long id;
    private String name;
    private LocalDateTime version;
    private double weightInGrams;

    @OneToMany
    private List<Nutrients> nutrientsList;




}
