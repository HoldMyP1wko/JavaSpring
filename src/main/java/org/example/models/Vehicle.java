package org.example.models;

import lombok.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Vehicle {
    private String id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private String category; // np. "Car", "Motorcycle", "Bus"

    @Builder.Default
    private Map<String, Object> additionalAttributes = new HashMap<>();

    public Vehicle(String id, String category, Map<String, Object> additionalAttributes, double price, int year, String model, String brand) {
        this.id = id;
        this.category = category;
        this.additionalAttributes = additionalAttributes;
        this.price = price;
        this.year = year;
        this.model = model;
        this.brand = brand;
    }

    public Vehicle(String id, String brand, String model, int year, double price, String category) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.category = category;
    }

    public void addAttribute(String key, Object value) {
        this.additionalAttributes.put(key, value);
    }

    public Vehicle copy() {
        return Vehicle.builder()
                .id(this.id)
                .brand(this.brand)
                .model(this.model)
                .year(this.year)
                .price(this.price)
                .category(this.category)
                .additionalAttributes(new HashMap<>(this.additionalAttributes))
                .build();
    }
}