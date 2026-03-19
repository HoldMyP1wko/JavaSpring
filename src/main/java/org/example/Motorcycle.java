package org.example;

public class Motorcycle extends Vehicle {
    DrivingLicense drivingLicense;
    String typeOfVehicle;

    public Motorcycle(String typeOfVehicle, String id, String brand, String model, int year, double price, boolean rented, DrivingLicense drivingLicense) {
        super(typeOfVehicle,id, brand, model, year, price, rented);
        this.drivingLicense = drivingLicense;
    }

    public Motorcycle(Motorcycle motorcycle){
        super(motorcycle);
        this.drivingLicense = motorcycle.drivingLicense;
        this.typeOfVehicle = motorcycle.typeOfVehicle;
    }

    @Override
    public Vehicle deepCopy() {
        return new Motorcycle(this);
    }
}
