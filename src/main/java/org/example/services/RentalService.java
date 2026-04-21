package org.example.services;

import org.example.models.Rental;
import org.example.repositories.RentalRepository;
import org.example.repositories.VehicleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RentalService {
    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;

    public RentalService(RentalRepository rentalRepository, VehicleRepository vehicleRepository) {
        this.rentalRepository = rentalRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public boolean vehicleHasActiveRental(String vehicleId) {
        return rentalRepository.findAll().stream()
                .anyMatch(r -> r.getVehicleId().equals(vehicleId) && r.getReturnDateTime() == null);
    }

    public Optional<Rental> findActiveRentalByUserId(String userId) {
        return rentalRepository.findAll().stream()
                .filter(r -> r.getUserId().equals(userId) && r.getReturnDateTime() == null)
                .findFirst();
    }

    public List<Rental> findUserRentals(String userId) {
        return rentalRepository.findAll().stream()
                .filter(r -> r.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    public void rentVehicle(String userId, String vehicleId) {
        if (vehicleRepository.findById(vehicleId).isEmpty()) {
            throw new IllegalArgumentException("Taki pojazd nie istnieje.");
        }
        if (findActiveRentalByUserId(userId).isPresent()) {
            throw new IllegalStateException("Masz już aktywne wypożyczenie! Zwróć najpierw obecny pojazd.");
        }
        if (vehicleHasActiveRental(vehicleId)) {
            throw new IllegalStateException("Ten pojazd jest już wypożyczony przez kogoś innego.");
        }

        Rental rental = new Rental(UUID.randomUUID().toString(), vehicleId, userId, String.valueOf(LocalDateTime.now()), null);
        rentalRepository.save(rental);
    }

    public void returnVehicle(String userId) {
        Rental activeRental = findActiveRentalByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Nie masz aktualnie żadnego wypożyczonego pojazdu."));

        activeRental.setReturnDateTime(String.valueOf(LocalDateTime.now()));
        rentalRepository.save(activeRental);
    }
}