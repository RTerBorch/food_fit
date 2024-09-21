package com.robintb.food_fit.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDateTime created;
    // TODO add createdBy, and list of users manyToMany

    @ManyToMany
    private List<FoodItem> ingredients;

    public Recipe(String name, List<FoodItem> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        created = LocalDateTime.now();
    }

    public double getTotalNutrientValue(String nutrientName) {
        return ingredients.stream()
                .flatMap(foodItem -> foodItem.getNutrientList().stream())
                .filter(nutrient -> nutrientName.equalsIgnoreCase(nutrient.getName()))
                .mapToDouble(Nutrient::getValue)
                .sum();
    }

}
