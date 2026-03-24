package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepository implements IVehicleRepository {
    private List<Vehicle> vehicles = new ArrayList<>();
    private final String FILE_NAME = "vehicles.csv";

    public VehicleRepository() {
        load();
    }

    @Override
    public void rentVehicle(String vehicleId) {
        Vehicle v = getVehicleInternal(vehicleId);
        if (v != null && !v.isRented()) {
            v.setRented(true);
            save();
        }
    }

    @Override
    public void returnVehicle(String vehicleId) {
        Vehicle v = getVehicleInternal(vehicleId);
        if (v != null && v.isRented()) {
            v.setRented(false);
            save();
        }
    }

    @Override
    public List<Vehicle> getVehicles() {
        List<Vehicle> copyList = new ArrayList<>();
        for (Vehicle v : vehicles) {
            copyList.add(v.copy());
        }
        return copyList;
    }

    @Override
    public Vehicle getVehicle(String id) {
        Vehicle v = getVehicleInternal(id);
        return v != null ? v.copy() : null;
    }

    private Vehicle getVehicleInternal(String id) {
        return vehicles.stream().filter(v -> v.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void add(Vehicle vehicle) {
        vehicles.add(vehicle.copy());
        save();
    }

    @Override
    public void remove(String vehicleId) {
        vehicles.removeIf(v -> v.getId().equals(vehicleId));
        save();
    }

    @Override
    public void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Vehicle v : vehicles) {
                pw.println(v.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Błąd zapisu pojazdów: " + e.getMessage());
        }
    }

    @Override
    public void load() {
        vehicles.clear();
        File file = new File(FILE_NAME);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");
                if (p[0].equalsIgnoreCase("Car")) {
                    vehicles.add(new Car(p[1], p[2], p[3], Integer.parseInt(p[4]), Double.parseDouble(p[5]), Boolean.parseBoolean(p[6])));
                } else if (p[0].equalsIgnoreCase("Motorcycle")) {
                    vehicles.add(new Motorcycle(p[1], p[2], p[3], Integer.parseInt(p[4]), Double.parseDouble(p[5]), Boolean.parseBoolean(p[6]), p[7]));
                }
            }
        } catch (Exception e) {
            System.err.println("Błąd odczytu pojazdów: " + e.getMessage());
        }
    }
}