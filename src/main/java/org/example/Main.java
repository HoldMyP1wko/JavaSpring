package org.example;

import org.example.models.*;
import org.example.repositories.UserRepository;
import org.example.repositories.VehicleRepository;
import org.example.repositories.impl.VehicleRepositoryImpl;
import org.example.services.AuthService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new org.example.repositories.impl.UserRepository();
        VehicleRepository vehicleRepository = new VehicleRepositoryImpl();
        AuthService auth = new AuthService(userRepository);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Witaj w systemie wypożyczalni!");
        User currentUser = null;

        while (currentUser == null) {
            System.out.println("\n1. Zaloguj się");
            System.out.println("2. Zarejestruj nowe konto");
            System.out.println("0. Wyjście");
            System.out.print("Wybierz opcję: ");
            String startChoice = scanner.nextLine();

            if (startChoice.equals("0")) return;

            System.out.print("Podaj login: ");
            String login = scanner.nextLine();
            System.out.print("Podaj hasło: ");
            String password = scanner.nextLine();

            if (startChoice.equals("1")) {
                currentUser = auth.login(login, password);
                if (currentUser == null) {
                    System.out.println("Nieprawidłowe dane logowania!");
                }
            } else if (startChoice.equals("2")) {
                boolean success = auth.register(login, password);
                if (success) {
                    System.out.println("Zarejestrowano pomyślnie! Możesz się teraz zalogować.");
                } else {
                    System.out.println("Błąd: Taki login już istnieje w systemie.");
                }
            } else {
                System.out.println("Nieprawidłowa opcja.");
            }
        }

        System.out.println("\nZalogowano pomyślnie jako: " + currentUser.getRole());

        boolean running = true;
        while (running) {
            if (currentUser.getRole() == Role.ADMIN) {
                System.out.println("\n--- MENU ADMIN ---");
                System.out.println("1. Dodaj pojazd");
                System.out.println("2. Usuń pojazd");
                System.out.println("3. Przeglądaj listę pojazdów");
                System.out.println("4. Wyświetl użytkowników i ich pojazdy");
                System.out.println("5. Usuń użytkownika");
                System.out.println("0. Wyjście");
                System.out.print("Wybór: ");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println("Dodawanie: (C)ar czy (M)otorcycle?");
                        String type = scanner.nextLine();
                        System.out.print("ID: "); String id = scanner.nextLine();
                        System.out.print("Marka: "); String brand = scanner.nextLine();
                        System.out.print("Model: "); String model = scanner.nextLine();
                        System.out.print("Rok: "); int year = Integer.parseInt(scanner.nextLine());
                        System.out.print("Cena: "); double price = Double.parseDouble(scanner.nextLine());

                        if (type.equalsIgnoreCase("C")) {
                            vehicleRepository.add(new Car(id, brand, model, year, price, false));
                        } else if (type.equalsIgnoreCase("M")) {
                            System.out.print("Kategoria: "); String cat = scanner.nextLine();
                            vehicleRepository.add(new Motorcycle(id, brand, model, year, price, false, cat));
                        }
                        System.out.println("Dodano pojazd.");
                        break;
                    case "2":
                        System.out.print("Podaj ID pojazdu do usunięcia: ");
                        vehicleRepository.remove(scanner.nextLine());
                        System.out.println("Usunięto.");
                        break;
                    case "3":
                        vehicleRepository.getVehicles().forEach(System.out::println);
                        break;
                    case "4":
                        for (User u : userRepository.getUsers()) {
                            System.out.print("User: " + u.getLogin() + " | Pojazd ID: " + (u.getRentedVehicleId() != null ? u.getRentedVehicleId() : "Brak"));
                            if (u.getRentedVehicleId() != null) {
                                Vehicle v = vehicleRepository.getVehicle(u.getRentedVehicleId());
                                if (v != null) System.out.print(" (" + v.getBrand() + " " + v.getModel() + ")");
                            }
                            System.out.println();
                        }
                    case "5":
                        System.out.print("Podaj login użytkownika do usunięcia: ");
                        String loginToRemove = scanner.nextLine();
                        if (loginToRemove.equals(currentUser.getLogin())) {
                            System.out.println("Nie możesz usunąć samego siebie!");
                        } else {
                            boolean removed = userRepository.removeUser(loginToRemove);
                            if (removed) {
                                System.out.println("Pomyślnie usunięto użytkownika.");
                            } else {
                                System.out.println("Błąd: Użytkownik nie istnieje lub posiada obecnie wypożyczony pojazd.");
                            }
                        }
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Nieznana opcja.");
                }

            } else {
                System.out.println("\n--- MENU USER ---");
                System.out.println("1. Wyświetl dostępne pojazdy");
                System.out.println("2. Wypożycz pojazd");
                System.out.println("3. Zwróć pojazd");
                System.out.println("4. Moje dane");
                System.out.println("0. Wyjście");
                System.out.print("Wybór: ");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        vehicleRepository.getVehicles().stream().filter(v -> !v.isRented()).forEach(System.out::println);
                        break;
                    case "2":
                        if (currentUser.getRentedVehicleId() != null) {
                            System.out.println("Masz już wypożyczony pojazd!");
                            break;
                        }
                        System.out.print("Podaj ID pojazdu do wypożyczenia: ");
                        String rentId = scanner.nextLine();
                        Vehicle vToRent = vehicleRepository.getVehicle(rentId);
                        if (vToRent != null && !vToRent.isRented()) {
                            vehicleRepository.rentVehicle(rentId);
                            currentUser.setRentedVehicleId(rentId);
                            userRepository.update(currentUser);
                            System.out.println("Wypożyczono pojazd.");
                        } else {
                            System.out.println("Pojazd niedostępny lub nie istnieje.");
                        }
                        break;
                    case "3":
                        if (currentUser.getRentedVehicleId() == null) {
                            System.out.println("Nie masz wypożyczonego pojazdu.");
                            break;
                        }
                        vehicleRepository.returnVehicle(currentUser.getRentedVehicleId());
                        currentUser.setRentedVehicleId(null);
                        userRepository.update(currentUser);
                        System.out.println("Pojazd zwrócony.");
                        break;
                    case "4":
                        System.out.println("Login: " + currentUser.getLogin());
                        if (currentUser.getRentedVehicleId() != null) {
                            Vehicle myVehicle = vehicleRepository.getVehicle(currentUser.getRentedVehicleId());
                            System.out.println("Wypożyczony pojazd: " + myVehicle.toString());
                        } else {
                            System.out.println("Brak wypożyczonych pojazdów.");
                        }
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Nieznana opcja.");
                }
            }
        }
        System.out.println("Koniec programu.");
    }
}