package com.robintb.food_fit.controllers;

import com.robintb.food_fit.services.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/food-import")
public class ImportFoodController {

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
}
