package org.example.repositories.impl;

import org.example.models.Role;
import org.example.models.User;
import org.example.repositories.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class UserRepositoryImpl implements UserRepository {
    private List<User> users = new ArrayList<>();
    private final String FILE_NAME = "users.csv";

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .map(User::copy)
                .findFirst();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equals(login))
                .map(User::copy)
                .findFirst();
    }

    @Override
    public User save(User user) {
        users.removeIf(u -> u.getId().equals(user.getId()));

        User copyToSave = user.copy();
        users.add(copyToSave);
        saveToFile();

        return copyToSave;
    }

    @Override
    public void deleteById(String id) {
        boolean removed = users.removeIf(u -> u.getId().equals(id));
        if (removed) {
            saveToFile();
        }
    }

    // --- METODY DO ZAPISU/ODCZYTU PLIKÓW ---

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User u : users) {
                pw.println(u.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Błąd zapisu użytkowników: " + e.getMessage());
        }
    }

    private void load() {
        users.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";", -1);

                // UWAGA: Format zależy od tego, jak wygląda Twoja metoda toCSV() w klasie User
                // Założyłem format CSV: id;login;password;role
                if (p.length >= 4) {
                    users.add(new User(p[0], p[1], p[2], Role.valueOf(p[3])));
                } else if (p.length == 3) {
                    // Opcja fallback dla starych danych: jeśli w pliku było tylko login;password;role
                    // Wtedy login traktujemy też jako tymczasowe ID
                    users.add(new User(p[0], p[0], p[1], Role.valueOf(p[2])));
                }
            }
        } catch (Exception e) {
            System.err.println("Błąd odczytu użytkowników: " + e.getMessage());
        }
    }
}