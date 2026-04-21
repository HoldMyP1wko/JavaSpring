package org.example;

import org.example.models.VehicleValidator;
import org.example.repositories.RentalRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.VehicleCategoryConfigRepository;
import org.example.repositories.impl.RentalJsonRepository;
import org.example.repositories.impl.UserJsonRepository;
import org.example.repositories.impl.VehicleCategoryConfigJsonRepository;
import org.example.repositories.impl.VehicleJsonRepository;
import org.example.services.*;

public class Main {
    public static void main(String[] args) {
        VehicleJsonRepository vehicleRepository = new VehicleJsonRepository();
        UserRepository userRepository = new UserJsonRepository();
        RentalRepository rentalRepository = new RentalJsonRepository();
        VehicleCategoryConfigRepository categoryConfigRepository = new VehicleCategoryConfigJsonRepository();

        AuthService authService = new AuthService(userRepository);
        VehicleCategoryConfigService categoryConfigService = new VehicleCategoryConfigService(categoryConfigRepository);
        VehicleValidator vehicleValidator = new VehicleValidator(categoryConfigService);

        RentalService rentalService = new RentalService(rentalRepository, vehicleRepository);

        VehicleService vehicleService = new VehicleService(vehicleRepository, rentalService, vehicleValidator);
        UserService userService = new UserService(userRepository, rentalService);

        UI ui = new UI(
                authService,
                vehicleService,
                rentalService,
                userService,
                categoryConfigService
        );

        ui.start();
    }
}