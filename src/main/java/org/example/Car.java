package org.example;


public class Car extends Vehicle {
    public Car(String id, String brand, String model, int year, double price, boolean rented) {
        super(id, brand, model, year, price, rented);
    }

    @Override
    public String toCSV() {
        return "Car;" + id + ";" + brand + ";" + model + ";" + year + ";" + price + ";" + rented;
    }

    @Override
    public Vehicle copy() {
        return new Car(this.id, this.brand, this.model, this.year, this.price, this.rented);
    }
}