package com.robintb.food_fit.services;

import com.robintb.food_fit.dtos.foodDTOs.RecipeDTO;
import com.robintb.food_fit.enums.foodEnums.NutrientType;
import com.robintb.food_fit.models.FoodItem;
import com.robintb.food_fit.models.Recipe;
import com.robintb.food_fit.repositories.FoodItemRepository;
import com.robintb.food_fit.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    FoodItemRepository foodItemRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe createRecipe(String name, List<Long> ingredientIds) {
        return recipeRepository.save(new Recipe(name, foodItemRepository.findAllById(ingredientIds)));
    }

    public List<RecipeDTO> returnAllRecipesAsDTO() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipeDTOS = new ArrayList<>();

        for (Recipe recipe : recipes) {

            recipeDTOS.add(new RecipeDTO(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getCreated(),
                    recipe.getTotalNutrientValue(NutrientType.CALORIES.getDisplayName()),
                    recipe.getTotalNutrientValue(NutrientType.FAT.getDisplayName()),
                    recipe.getTotalNutrientValue(NutrientType.PROTEIN.getDisplayName()),
                    recipe.getTotalNutrientValue(NutrientType.CARBS.getDisplayName()),
                    recipe.getIngredients()));
        }
        return recipeDTOS;
    }

    public Recipe editRecipe(Long id, String name, List<Long> ingredients) throws IllegalArgumentException {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        recipe.setName(name);

        List<FoodItem> foodItems = foodItemRepository.findAllById(ingredients);
        recipe.setIngredients(foodItems);

        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) throws IllegalArgumentException {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        recipeRepository.delete(recipe);
    }
}
