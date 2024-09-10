package com.robintb.food_fit.repositories;

import com.robintb.food_fit.models.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportFoodRepository extends JpaRepository<FoodItem, Long> {
}
