package com.robintb.food_fit.controllers;

import com.robintb.food_fit.repositories.ImportFoodRepository;
import com.robintb.food_fit.services.ImportFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/food-import")
public class ImportFoodController {

    @Autowired
    private ImportFoodService importFoodService;

    @Autowired
    private ImportFoodRepository importFoodRepository;

    @GetMapping("/test")
    public String testingApi(){
        return "TESTING WORKED";
    }

    @PostMapping("/collect-food-data")
    @ResponseBody
    public String collectFoodData(@RequestParam int foodLimit){
        importFoodService.collectFoodData(foodLimit);
        return "Collected food data."; // TODO add error message and response message
    }
}
