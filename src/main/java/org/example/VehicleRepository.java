package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VehicleRepository implements IVehicleRepository {
    private final List<Vehicle> vehiclesList = new ArrayList<>();
    @Override
    public void rentVehicle(Vehicle vehicle) {

    }

    @Override
    public Vehicle returnVehicle(Vehicle vehicle) {
        return null;
    }

    @Override
    public List<Vehicle> getVehicles() {
        return List.of();
    }

    @Override
    public void save() {

    }

    public void load(File file) {
    try (Scanner scanner = new Scanner (file)){
        while(scanner.hasNextLine()){
            String[] parts = scanner.nextLine().split(";");
            String className = parts[0];
            if (className.equals(Car.class.getName().toUpperCase())){
                String id = parts[1];
                String brand = parts[2];
                String model = parts[3];
                int year = Integer.parseInt(parts[4]);
                double price = Double.parseDouble(parts[5]);
                boolean isRented = Boolean.parseBoolean(parts[6]);

                Car car = new Car(className, id, brand, model, year, price, isRented);

                vehiclesList.add(car);

            } else {
                className = Motorcycle.class.getName().toUpperCase();
                String id = parts[1];
                String brand = parts[2];
                String model = parts[3];
                int year = Integer.parseInt(parts[4]);
                double price = Double.parseDouble(parts[5]);
                boolean isRented = Boolean.parseBoolean(parts[6]);
                DrivingLicense drivingLicense = DrivingLicense.valueOf(parts[7]);

                Motorcycle motorcycle = new Motorcycle(className, id, brand, model, year,price, isRented, drivingLicense);

                vehiclesList.add(motorcycle);
            }
        }
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
}

    @Override
    public void add() {

    }

    @Override
    public void remove() {

    }

    @Override
    public Vehicle getVehicle(Vehicle vehicle) {
    return null;
}

}

