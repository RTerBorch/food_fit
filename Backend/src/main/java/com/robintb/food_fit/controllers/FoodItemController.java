package com.robintb.food_fit.controllers;

import com.robintb.food_fit.models.FoodItem;
import com.robintb.food_fit.models.Recipe;
import com.robintb.food_fit.services.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/food-import")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    @GetMapping("/test")
    public String testingApi(){
        return "TESTING WORKED";
    }

    @PostMapping("/collect-food-data")
    @ResponseBody
    public String collectFoodData(@RequestParam int foodLimit){
        foodItemService.collectFoodData(foodLimit);
        return "Collected food data."; // TODO add error message and response message
    }

    @PostMapping("/get-foot-items-by-search")
    @ResponseBody
    public ResponseEntity<?> getFoodItemsBySearch(@RequestParam String keyword) {

        try {
            List<FoodItem> foodItems = foodItemService.searchFoodItems(keyword);
            return new ResponseEntity<>(foodItems, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
