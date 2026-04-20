package org.example.services;

import org.example.models.Rental;
import org.example.repositories.RentalRepository;
import org.example.repositories.VehicleRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class RentalService {
    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;

    public RentalService(RentalRepository rentalRepository, VehicleRepository vehicleRepository) {
        this.rentalRepository = rentalRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public boolean rentVehicle(String userLogin, String vehicleId) {
        if (vehicleRepository.findById(vehicleId).isEmpty()) {
            System.out.println("Błąd: Taki pojazd nie istnieje.");
            return false;
        }

        Optional<Rental> activeRental = rentalRepository.findByVehicleIdAndReturnDateIsNull(vehicleId);

        if (activeRental.isPresent()) {
            System.out.println("Błąd: Pojazd jest już aktualnie wypożyczony.");
            return false;
        }

        Rental newRental = new Rental(
                UUID.randomUUID().toString(),
                vehicleId,
                userLogin,
                LocalDateTime.now().toString(),
                null
        );

        rentalRepository.save(newRental);
        return true;
    }

    public boolean returnVehicle(String userLogin, String vehicleId) {
        Optional<Rental> activeRental = rentalRepository.findByVehicleIdAndReturnDateIsNull(vehicleId);

        if (activeRental.isEmpty()) {
            System.out.println("Błąd: Ten pojazd nie jest obecnie wypożyczony.");
            return false;
        }

        Rental rentalToReturn = activeRental.get();

        if (!rentalToReturn.getUserId().equals(userLogin)) {
            System.out.println("Błąd: Nie możesz zwrócić pojazdu wypożyczonego przez inną osobę.");
            return false;
        }

        rentalToReturn.setReturnDateTime(LocalDateTime.now().toString());
        rentalRepository.save(rentalToReturn);

        return true;
    }

    public boolean hasActiveRentals(String userLogin) {
        return rentalRepository.findAll().stream()
                .anyMatch(r -> r.getUserId().equals(userLogin) && r.getReturnDateTime() == null);
    }
}