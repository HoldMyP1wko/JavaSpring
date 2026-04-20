package org.example.services;

import org.example.models.Rental;
import org.example.models.Vehicle;
import org.example.models.VehicleValidator;
import org.example.repositories.RentalRepository;
import org.example.repositories.VehicleRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VehicleService {
    private VehicleRepository vehicleRepo;
    private RentalRepository rentalRepo;
    private VehicleValidator validator;

    public VehicleService(VehicleRepository vehicleRepo, RentalRepository rentalRepo, VehicleValidator validator) {
        this.vehicleRepo = vehicleRepo;
        this.rentalRepo = rentalRepo;
        this.validator = validator;
    }

    public void addVehicle(Vehicle vehicle) {
        try {
            validator.validate(vehicle);
            vehicleRepo.save(vehicle);
        } catch (IllegalArgumentException e) {
            System.out.println("Błąd walidacji: " + e.getMessage());
        }
    }

    public boolean removeVehicle(String vehicleId) {
        boolean isRented = rentalRepo.findByVehicleIdAndReturnDateIsNull(vehicleId).isPresent();
        if (isRented) {
            System.out.println("Nie można usunąć pojazdu, który jest aktualnie wypożyczony.");
            return false;
        }
        vehicleRepo.deleteById(vehicleId);
        return true;
    }

    public List<Vehicle> getAvailableVehicles(){
        Set<String> rentedVehiclesId = rentalRepo.findAll().stream()
                .filter(Rental::isActive)
                .map(Rental::getVehicleId)
                .collect(Collectors.toSet());

        return vehicleRepo.findAll().stream().
                filter(vehicle -> !rentedVehiclesId.contains(vehicle.getId()))
                .collect(Collectors.toList());
    }
}