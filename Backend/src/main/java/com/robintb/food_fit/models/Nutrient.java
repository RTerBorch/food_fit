package com.robintb.food_fit.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Nutrient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String enhet;
    private Double value;
    private Double weightGram;

    public Nutrient(String name, String enhet, Double value, Double weightGram) {
        this.name = name;
        this.enhet = enhet;
        this.value = value;
        this.weightGram = weightGram;
    }
}
