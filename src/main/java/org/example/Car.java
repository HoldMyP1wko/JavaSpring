package org.example;

public class Car extends Vehicle {
    public Car(String typeOfVehicle, String id, String brand, String model, int year, double price, boolean rented) {
        super(typeOfVehicle,id, brand, model, year, price, rented);
    }

    public Car(Car car) {
        super(car);
        this.typeOfVehicle = car.typeOfVehicle;
    }

    @Override
    public Vehicle deepCopy(){
        return new Car(this);
    }
}
