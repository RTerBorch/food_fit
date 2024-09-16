package com.robintb.food_fit.repositories;

import com.robintb.food_fit.models.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    Optional<FoodItem> findByName(String name);
    List<FoodItem> findAllByIds(List<Long> ids);
}
