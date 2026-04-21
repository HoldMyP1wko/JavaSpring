package org.example.models;

import lombok.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Vehicle {
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String brand;
    private String model;
    private int year;
    private double price;
    private String plate;
    private String category;

    @Builder.Default
    private Map<String, Object> additionalAttributes = new HashMap<>();

    public void addAttribute(String key, Object value) {
        if (this.additionalAttributes == null) {
            this.additionalAttributes = new HashMap<>();
        }
        this.additionalAttributes.put(key, value);
    }

    public Vehicle copy() {
        return Vehicle.builder()
                .id(this.id)
                .brand(this.brand)
                .model(this.model)
                .year(this.year)
                .price(this.price)
                .plate(this.plate)
                .category(this.category)
                .additionalAttributes(new HashMap<>(this.additionalAttributes))
                .build();
    }
}