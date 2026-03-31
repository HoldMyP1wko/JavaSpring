package org.example.repositories.impl;

import org.example.models.Car;
import org.example.models.Motorcycle;
import org.example.models.Vehicle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.util.Optional;

public class VehicleRepository implements org.example.repositories.VehicleRepository {
    private List<Vehicle> vehicles = new ArrayList<>();
    private final String FILE_NAME = "vehicles.csv";

    public VehicleRepository() {
        load();
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Vehicle v : vehicles) {
                pw.println(v.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Błąd zapisu pojazdów: " + e.getMessage());
        }
    }

    private void load() {
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

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> copyList = new ArrayList<>();
        for (Vehicle v : vehicles) {
            copyList.add(v.copy());
        }
        return copyList;
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        return vehicles.stream().filter(v -> v.getId().equals(id)).map(Vehicle::copy).findFirst();
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        vehicles.removeIf(v ->v.getId().equals(vehicle.getId()));
        Vehicle copyToSave = vehicle.copy();
        vehicles.add(copyToSave);
        saveToFile();
        return copyToSave;
    }

    @Override
    public void deleteById(String id) {
        boolean removed = vehicles.removeIf(v -> v.getId().equals(id));
        if (removed) {
            saveToFile();
        }
    }
}