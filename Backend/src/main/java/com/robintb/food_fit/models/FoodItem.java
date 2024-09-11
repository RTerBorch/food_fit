package com.robintb.food_fit.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class FoodItem {

    @Id
    private Long id;
    private String name;
    private LocalDateTime version;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Nutrient> nutrientList;

    public FoodItem(Long id, String name, LocalDateTime version, List<Nutrient> nutrientList) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.nutrientList = nutrientList;
    }
}
