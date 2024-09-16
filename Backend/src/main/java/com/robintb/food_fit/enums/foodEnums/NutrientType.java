package com.robintb.food_fit.enums.foodEnums;

import java.util.HashMap;
import java.util.Map;

public enum NutrientType {

    CALORIES("Energy (kcal)"),
    PROTEIN("Protein"),
    FAT("Fat, total"),
    CARBS("Carbohydrates, available");

    private final String displayName;
    private static final Map<String, NutrientType> NUTRIENT_TYPE_MAP = new HashMap<>();

    static {
        for (NutrientType nutrientType : NutrientType.values()) {
            NUTRIENT_TYPE_MAP.put(nutrientType.getDisplayName(), nutrientType);
        }
    }

    NutrientType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Map<String, NutrientType> getNutrientMap() {
        return NUTRIENT_TYPE_MAP;
    }

    public static NutrientType fromDisplayName(String displayName) {
        return NUTRIENT_TYPE_MAP.get(displayName);
    }
}
