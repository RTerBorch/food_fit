package com.robintb.food_fit.repositories;

import com.robintb.food_fit.models.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    Optional<FoodItem> findByName(String name);

    @Query("SELECT f FROM FoodItem f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<FoodItem> searchByName(@Param("keyword") String keyword);

    // Scoring system to give better search results.
    @Query("SELECT f FROM FoodItem f " +
            "WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY " +
            "CASE " +
            "WHEN LOWER(f.name) = LOWER(:keyword) THEN 3 " +
            "WHEN LOWER(f.name) LIKE LOWER(CONCAT(:keyword, '%')) THEN 2 " +
            "WHEN LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 1 " +
            "ELSE 0 END DESC")  // Sort in descending order of the score
    List<FoodItem> searchByNameWithRelevance(@Param("keyword") String keyword);
}
