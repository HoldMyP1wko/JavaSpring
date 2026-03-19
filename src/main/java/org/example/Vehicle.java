package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Vehicle{
    String id;
    String brand;
    String model;
    int year;
    double price;
    boolean isRented;
    String typeOfVehicle;

    public Vehicle(String typeOfVehicle,String id, String brand, String model, int year, double price, boolean rented) {
        this.typeOfVehicle = typeOfVehicle;
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.isRented = rented;
    }

    public Vehicle(Vehicle vehicle){
        this.typeOfVehicle = vehicle.typeOfVehicle;
        this.id = vehicle.id;
        this.brand = vehicle.brand;
        this.model = vehicle.model;
        this.year = vehicle.year;
        this.price = vehicle.price;
        this.isRented = vehicle.isRented;
    }

    public abstract Vehicle deepCopy();

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean b){
        this.isRented = b;
    }

    public String toCSV(){
        return  this.typeOfVehicle + ";" + this.id + ";" + this.brand + ";" + this.model +  ";" + this.year
                + ";" + this.price + ";" + this.isRented;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", isRented=" + isRented +
                ", typeOfVehicle='" + typeOfVehicle + '\'' +
                '}';
    }
}
