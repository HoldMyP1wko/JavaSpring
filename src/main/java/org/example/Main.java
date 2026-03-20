package org.example;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User activeUser = null;
        UserRepository userRepository = new UserRepository();

        System.out.println("Witaj w systmie wynajmu pojazdów. Zaloguj sie: ");
        String login = "user";
        String password = "user123";
        if (Authentication.passedAuth(login, password)){
            activeUser = new User(login, password, Role.USER);
            System.out.println("Zalogowano jako " + login);
        } else {
            System.out.println("Błąd logowania, sprawdź ponownie login i hasło");
        }

        if (activeUser != null && activeUser.getRole().equals(Role.ADMIN)){
            int choice = 0;
            System.out.println("""
                    Wybierz co chcesz zrobić:
                    1 - Wypożycz pojaz,
                    2 - Zwróć pojazd,
                    3 - Wyświetl swoje dane""");
            choice = sc.nextInt();
            switch (choice){
                case 1:
            }
        }
    }
}