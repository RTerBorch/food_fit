package com.robintb.food_fit.services;


import com.robintb.food_fit.models.FoodItem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class ImportFoodService {

    private static final HashSet<String> NUTRIENTS_OF_INTEREST = new HashSet<>();

    static {
        NUTRIENTS_OF_INTEREST.add("Energi (kcal)");
        NUTRIENTS_OF_INTEREST.add("Fett");
        NUTRIENTS_OF_INTEREST.add("Protein");
        NUTRIENTS_OF_INTEREST.add("Kolhydrater");
    }


    public void collectFoodData(int foodLimit) {

        List<FoodItem> foodItems = new ArrayList<>();

        try {
            URL url = new URL("https://dataportal.livsmedelsverket.se/livsmedel/api/v1/livsmedel?offset=0&limit="
                    + foodLimit + "&sprak=2");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
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

                // Parse string into a JSON object
                JSONParser jsonParser = new JSONParser();
                JSONObject dataObject = (JSONObject) jsonParser.parse(inline);

                System.out.println(dataObject.keySet());
                JSONArray livsmedelArray = (JSONArray) dataObject.get("livsmedel");


                // foodItem name
                for (Object livsmedel : livsmedelArray) {
                    JSONObject foodItem = (JSONObject) livsmedel;
                    System.out.println("version: " + foodItem.get("version"));
                    System.out.println("livsmedelnamn: " + foodItem.get("namn"));
                    JSONArray linksArray = (JSONArray) foodItem.get("links");

                    String link = "";
                    for (Object JSONLinks : linksArray){
                        System.out.println("teeeest: " + JSONLinks);
                        JSONObject linkObject = (JSONObject) JSONLinks;

                        System.out.println("rel:  " + linkObject.get("rel"));

                        if(linkObject.get("rel").equals("naringvarden")){
                            link = linkObject.get("href").toString();
                        }

                    }
                    System.out.println("Processed link:" + link);

                    JSONObject nutrientLink = (JSONObject) linksArray.get(0);
                    System.out.println(nutrientLink.get("href"));

                    collectNutrientData(nutrientLink.get("href").toString());
                }


            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    private void collectNutrientData(String href) {
        System.out.println("link: " + href);
/*

        try {
        URL url = new URL(href);

        HttpURLConnection connection = null;

        connection.setRequestMethod("GET");
        connection.connect();

            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/


    }
}
