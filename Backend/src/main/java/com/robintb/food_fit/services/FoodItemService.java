package com.robintb.food_fit.services;


import com.robintb.food_fit.enums.foodEnums.NutrientType;
import com.robintb.food_fit.models.FoodItem;
import com.robintb.food_fit.models.Nutrient;
import com.robintb.food_fit.repositories.FoodItemRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FoodItemService {

    @Autowired
    private FoodItemRepository foodItemRepository;

    private final String baseUrl = "https://dataportal.livsmedelsverket.se/livsmedel";

    public void collectFoodData(int foodLimit) {

        String method = "GET";
        String href = baseUrl + "/api/v1/livsmedel?offset=0&limit=" + foodLimit + "&sprak=2";
        List<FoodItem> foodItems = new ArrayList<>();
        JSONObject foodItemJSONObject = (JSONObject) CollectJSONDataFromLink(href, method);

        // DEBUG
        System.out.println(foodItemJSONObject.keySet());
        JSONArray livsmedelArray = (JSONArray) foodItemJSONObject.get("livsmedel");

        // foodItem name

        for (Object livsmedel : livsmedelArray) {

            JSONObject foodItemJSONOBJECT = (JSONObject) livsmedel;
            JSONArray linksArray = (JSONArray) foodItemJSONOBJECT.get("links");
            String nutritionAPILink = "";
            String nutritionAPIMethod = "";

            // Prepare for FoodItem creation
            Long foodItemId = Long.parseLong(foodItemJSONOBJECT.get("nummer").toString());
            String foodItemName = foodItemJSONOBJECT.get("namn").toString();
            LocalDateTime version = LocalDateTime.parse(foodItemJSONOBJECT.get("version").toString());
            List<Nutrient> foodItemNutrients = new ArrayList<>();

            // Collect URL from href tag and method for restAPI to collect nutrition data of footItem.
            for (Object JSONLinks : linksArray) {
                JSONObject linkObject = (JSONObject) JSONLinks;

                System.out.println("rel:  " + linkObject.get("rel"));

                if (linkObject.get("rel").equals("naringvarden")) {
                    nutritionAPILink = baseUrl + linkObject.get("href").toString();
                    nutritionAPIMethod = linkObject.get("method").toString();
                }
            }
            JSONArray nutrientJSONArray = (JSONArray) CollectJSONDataFromLink(nutritionAPILink, nutritionAPIMethod);
            System.out.println("Nutrient JSON object: " + nutrientJSONArray);

            for (Object nutrientItem : nutrientJSONArray){
                JSONObject nutrientJSONObject = (JSONObject) nutrientItem;

                String nutrientName = nutrientJSONObject.get("namn").toString();
                if (NutrientType.getNutrientMap().containsKey(nutrientName)){

                    foodItemNutrients.add(new Nutrient(
                            nutrientJSONObject.get("namn").toString(),
                            nutrientJSONObject.get("enhet").toString(),
                            Double.valueOf(nutrientJSONObject.get("varde").toString()),
                            Double.valueOf(nutrientJSONObject.get("viktGram").toString())
                            ));

                    }
                }
            System.out.println(foodItemNutrients.size() + " Size");
            updateFoodItemIfNewer(foodItemId, foodItemName,version,foodItemNutrients);
        }
    }

    private Object CollectJSONDataFromLink(String href, String method) {

        System.out.println("Method: " + method);
        System.out.println("Link: " + href);

        try {
            URL url = new URL(href);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                JSONParser jsonParser = new JSONParser();
                Object parsedData = jsonParser.parse(inline); // Parse the JSON data into a generic Object

                if (parsedData instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) parsedData;
                    System.out.println("It's a JSONObject!");
                    return jsonObject;  // Return the JSONObject or handle it accordingly
                } else if (parsedData instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) parsedData;
                    System.out.println("It's a JSONArray!");
                    return jsonArray;  // Return the JSONArray or handle it accordingly
                } else {
                    throw new IllegalArgumentException("Unexpected JSON type");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    public void updateFoodItemIfNewer(Long foodItemId, String foodItemName, LocalDateTime newVersion, List<Nutrient> foodItemNutrients) {

        // Check if the food item already exists in the database
        Optional<FoodItem> optionalFoodItem = foodItemRepository.findById(foodItemId);

        if (optionalFoodItem.isPresent()) {
            FoodItem existingFoodItem = optionalFoodItem.get();

            // Compare the version
            if (existingFoodItem.getVersion().isBefore(newVersion)) {
                // The new version is more recent, update the fields
                existingFoodItem.setName(foodItemName);
                existingFoodItem.setVersion(newVersion);
                existingFoodItem.setNutrientList(foodItemNutrients);

                // Save the updated food item
                foodItemRepository.save(existingFoodItem);
            } else {
                // The existing version is the same or newer, no update required
                System.out.println("The existing food item version is up to date.");
            }
        } else {
            // If the food item doesn't exist, create a new one
            FoodItem newFoodItem = new FoodItem(foodItemId, foodItemName, newVersion, foodItemNutrients);
            foodItemRepository.save(newFoodItem);
        }
    }

    public List<FoodItem> searchFoodItems(String keyword){
        return foodItemRepository.searchByNameWithRelevance(keyword);
    }

}

