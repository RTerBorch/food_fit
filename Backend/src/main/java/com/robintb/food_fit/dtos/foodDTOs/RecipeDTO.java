package com.robintb.food_fit.dtos.foodDTOs;

import com.robintb.food_fit.models.FoodItem;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecipeDTO {

    private Long id;
    private String name;
    private LocalDateTime created;

    private Double caloriesInRecipe;
    private Double fatInRecipe;
    private Double proteinInRecipe;
    private Double carbsInRecipe;

    private List<FoodItem> ingredients;

    public RecipeDTO(Long id, String name, LocalDateTime created, double caloriesInRecipe, double fatInRecipe,
                     double proteinInRecipe, double carbsInRecipe, List<FoodItem> ingredients) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.caloriesInRecipe = caloriesInRecipe;
        this.fatInRecipe = fatInRecipe;
        this.proteinInRecipe = proteinInRecipe;
        this.carbsInRecipe = carbsInRecipe;
        this.ingredients = ingredients;
    }
}
