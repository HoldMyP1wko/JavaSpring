package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IUserRepository userRepository = new UserRepository();
        IVehicleRepository vehicleRepository = new VehicleRepository();
        Authentication auth = new Authentication(userRepository);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Witaj w systemie wypożyczalni!");
        System.out.print("Podaj login: ");
        String login = scanner.nextLine();
        System.out.print("Podaj hasło: ");
        String password = scanner.nextLine();

        User currentUser = auth.login(login, password);

        if (currentUser == null) {
            System.out.println("Nieprawidłowe dane logowania.");
            return;
        }

        System.out.println("Zalogowano pomyślnie jako: " + currentUser.getRole());
        System.out.println("Załadowano pojazdów: " + vehicleRepository.getVehicles().size());

        boolean running = true;
        while (running) {
            if (currentUser.getRole() == Role.ADMIN) {
                System.out.println("\n--- MENU ADMIN ---");
                System.out.println("1. Dodaj pojazd");
                System.out.println("2. Usuń pojazd");
                System.out.println("3. Przeglądaj listę pojazdów");
                System.out.println("4. Wyświetl użytkowników i ich pojazdy");
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