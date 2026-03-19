package org.example;

import java.io.File;
import java.util.List;

public interface IVehicleRepository {

    public void rentVehicle(Vehicle vehicle);
    public Vehicle returnVehicle(Vehicle vehicle);
    public List<Vehicle> getVehicles();
    public void save();
    public void load(File file);
    public void add();
    public void remove();
    public Vehicle getVehicle(Vehicle vehicle);

}
