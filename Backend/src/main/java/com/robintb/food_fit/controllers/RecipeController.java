package com.robintb.food_fit.controllers;


import com.robintb.food_fit.dtos.foodDTOs.RecipeDTO;
import com.robintb.food_fit.models.FoodItem;
import com.robintb.food_fit.models.Recipe;
import com.robintb.food_fit.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/show-all-recipes")
    public ResponseEntity<List<RecipeDTO>> returnAllRecipesAsDTO(){
        List<RecipeDTO> recipeDTOs = recipeService.returnAllRecipesAsDTO();
        if (recipeDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 if no recipes found
        }
        return new ResponseEntity<>(recipeDTOs, HttpStatus.OK); // 200 OK with recipes
    }

    @PostMapping("/create-new-recipe")
    @ResponseBody
    public ResponseEntity<?> createRecipe(@RequestParam String name, @RequestBody List<Long> ingredients) {
        try {
            Recipe recipe = recipeService.createRecipe(name, ingredients);
            return new ResponseEntity<>(recipe, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
