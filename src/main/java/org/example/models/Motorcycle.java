package org.example.models;

public class Motorcycle extends Vehicle {
    private String category;

    public Motorcycle(String id, String brand, String model, int year, double price, boolean rented, String category) {
        super(id, brand, model, year, price, rented);
        this.category = category;
    }

    @Override
    public String toCSV() {
        return "Motorcycle;" + id + ";" + brand + ";" + model + ";" + year + ";" + price + ";" + rented + ";" + category;
    }

    @Override
    public Vehicle copy() {
        return new Motorcycle(this.id, this.brand, this.model, this.year, this.price, this.rented, this.category);
    }

    @Override
    public String toString() {
        return super.toString() + " | Kat: " + category;
    }
}