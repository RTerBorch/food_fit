package com.robintb.food_fit.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @Column(name = "person_name")
    private String name;

    @Column(name = "hashed_password")
    private String password;

    @Column(name = "email_address")
    private String email;

    @Column(name = "body_weight")
    private double weight;

    @Column(name = "user_age")
    private int age;

    public Person(String username, String name, String password, String email, double weight, int age) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.weight = weight;
        this.age = age;
    }
}
