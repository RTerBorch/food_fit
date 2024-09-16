package com.robintb.food_fit.repositories;

import com.robintb.food_fit.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository <Recipe, Long> {
}
