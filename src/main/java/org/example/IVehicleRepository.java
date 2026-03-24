package org.example;

import java.io.File;
import java.util.List;

import java.util.List;

public interface IVehicleRepository {
    void rentVehicle(String vehicleId);
    void returnVehicle(String vehicleId);
    List<Vehicle> getVehicles();
    Vehicle getVehicle(String id);
    void add(Vehicle vehicle);
    void remove(String vehicleId);
}