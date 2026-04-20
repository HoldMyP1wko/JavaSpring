package org.example;

import org.example.models.Role;
import org.example.models.User;
import org.example.models.Vehicle;
import org.example.services.*;

import java.util.Scanner;

public class UI {

    private final AuthService authService;
    private final VehicleService vehicleService;
    private final RentalService rentalService;
    private final UserService userService;
    private final VehicleCategoryConfigService categoryConfigService;
    private final Scanner scanner = new Scanner(System.in);
    private User currentUser;

    // Konstruktor: 1. Auth, 2. Vehicle, 3. Rental, 4. User, 5. Config
    // Kolejność musi być identyczna jak w Twoim Main.java!
    public UI(AuthService authService,
              VehicleService vehicleService,
              RentalService rentalService,
              UserService userService,
              VehicleCategoryConfigService categoryConfigService) {
        this.authService = authService;
        this.vehicleService = vehicleService;
        this.rentalService = rentalService;
        this.userService = userService;
        this.categoryConfigService = categoryConfigService;
    }

    public void start() {
        while (true) {
            System.out.println("\n=== SYSTEM WYPOŻYCZALNI ===\n1. Zaloguj\n2. Zarejestruj\n0. Wyjście");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                handleLogin();
                if (currentUser != null) {
                    mainMenu();
                }
            } else if (choice.equals("2")) {
                handleRegister();
            } else if (choice.equals("0")) {
                break;
            }
        }
    }

    private void handleLogin() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Hasło: ");
        String password = scanner.nextLine();
        currentUser = authService.login(login, password);
        if (currentUser == null) {
            System.out.println("Błąd logowania!");
        }
    }

    private void handleRegister() {
        System.out.print("Nowy login: ");
        String login = scanner.nextLine();
        System.out.print("Nowe hasło: ");
        String password = scanner.nextLine();
        if (authService.register(login, password)) {
            System.out.println("Zarejestrowano pomyślnie.");
        } else {
            System.out.println("Login zajęty!");
        }
    }

    private void mainMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- MENU (" + currentUser.getRole() + ") ---");
            if (currentUser.getRole() == Role.ADMIN) {
                System.out.println("1. Dodaj pojazd (Config-Driven)\n2. Usuń pojazd\n3. Lista użytkowników\n0. Wyloguj");
            } else {
                System.out.println("1. Lista dostępnych pojazdów\n2. Wypożycz\n3. Zwróć\n0. Wyloguj");
            }

            String choice = scanner.nextLine().trim();
            if (choice.equals("0")) {
                currentUser = null;
                loggedIn = false;
            } else if (currentUser.getRole() == Role.ADMIN) {
                handleAdminChoice(choice);
            } else {
                handleUserChoice(choice);
            }
        }
    }

    private void handleAdminChoice(String choice) {
        switch (choice) {
            case "1" -> addVehicleConfigDriven();
            case "2" -> {
                System.out.print("ID pojazdu do usunięcia: ");
                vehicleService.removeVehicle(scanner.nextLine());
            }
            // Tu możesz dodać kolejne opcje admina
        }
    }

    private void handleUserChoice(String choice) {
        switch (choice) {
            case "1" -> vehicleService.getAvailableVehicles().forEach(System.out::println);
            case "2" -> {
                System.out.print("ID pojazdu: ");
                rentalService.rentVehicle(currentUser.getLogin(), scanner.nextLine());
            }
            case "3" -> {
                System.out.print("ID pojazdu do zwrotu: ");
                rentalService.returnVehicle(currentUser.getLogin(), scanner.nextLine());
            }
        }
    }

    private void addVehicleConfigDriven() {
        System.out.print("Podaj kategorię (np. Car, Motorcycle, Bus): ");
        String category = scanner.nextLine();

        org.example.models.CategoryConfig config = categoryConfigService.getConfigByName(category);
        if (config == null) {
            System.out.println("Nieznana kategoria!");
            return;
        }

        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Marka: "); String brand = scanner.nextLine();
        System.out.print("Model: "); String model = scanner.nextLine();
        System.out.print("Rok: "); int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Cena: "); double price = Double.parseDouble(scanner.nextLine());

        Vehicle vehicle = new Vehicle(id, brand, model, year, price, category);

        // Dynamiczne pobieranie atrybutów z JSONa
        config.getAttributes().forEach((attrName, attrType) -> {
            System.out.print("Podaj " + attrName + " (" + attrType + "): ");
            String rawValue = scanner.nextLine();

            Object value = switch (attrType.toLowerCase()) {
                case "string" -> rawValue;
                case "integer" -> Integer.parseInt(rawValue);
                case "number" -> Double.parseDouble(rawValue);
                case "boolean" -> Boolean.parseBoolean(rawValue);
                default -> rawValue;
            };
            vehicle.addAttribute(attrName, value);
        });

        vehicleService.addVehicle(vehicle);
    }
}