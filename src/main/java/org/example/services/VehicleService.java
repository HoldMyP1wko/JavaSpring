package org.example.services;

import org.example.models.Vehicle;
import org.example.models.VehicleValidator;
import org.example.repositories.VehicleRepository;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final RentalService rentalService;
    private final VehicleValidator vehicleValidator;

    public VehicleService(VehicleRepository vehicleRepository, RentalService rentalService, VehicleValidator vehicleValidator) {
        this.vehicleRepository = vehicleRepository;
        this.rentalService = rentalService;
        this.vehicleValidator = vehicleValidator;
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle findById(String id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono pojazdu o ID: " + id));
    }

    public boolean isVehicleRented(String id) {
        return rentalService.vehicleHasActiveRental(id);
    }

    public List<Vehicle> findAvailableVehicles() {
        return vehicleRepository.findAll().stream()
                .filter(v -> !isVehicleRented(v.getId()))
                .collect(Collectors.toList());
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        vehicleValidator.validate(vehicle);
        return vehicleRepository.save(vehicle);
    }

    public void removeVehicle(String vehicleId) {
        if (isVehicleRented(vehicleId)) {
            throw new IllegalStateException("Nie można usunąć pojazdu, który jest aktualnie wypożyczony.");
        }
        findById(vehicleId);
        vehicleRepository.deleteById(vehicleId);
    }
}