package org.example;

public abstract class Vehicle {
    protected String id;
    protected String brand;
    protected String model;
    protected int year;
    protected double price;
    protected boolean rented;

    public Vehicle(String id, String brand, String model, int year, double price, boolean rented) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.rented = rented;
    }

    public abstract String toCSV();
    public abstract Vehicle copy();

    public String getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public boolean isRented() { return rented; }
    public void setRented(boolean rented) { this.rented = rented; }

    @Override
    public String toString() {
        return "ID: " + id + " | " + brand + " " + model + " (" + year + ") | Cena: " + price + " | Wypożyczony: " + rented;
    }
}