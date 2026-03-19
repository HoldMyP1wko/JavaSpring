package org.example;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Witaj w systmie. Zaloguj sie: ");
        String login = sc.nextLine();
        String password = sc.nextLine();
        if (User.login(login, password)){
            System.out.println("Zalogowano jako " + login);
        } else {
            System.out.println("Błąd logowania sprawdź ponownie login i hasło");
        }

    }
}