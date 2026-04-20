package org.example.repositories.impl;

import org.example.models.Role;
import org.example.models.User;
import org.example.repositories.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class UserJsonRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();
    private final String FILE_NAME = "users.csv";

    public UserJsonRepository(){
        load();
    }

    @Override
    public List<User> findAll() {
        return users.stream().toList();
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream()
                .filter(u -> u.getLogin().equals(id))
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
        users.removeIf(u -> u.getLogin().equals(user.getLogin()));

        User copyToSave = user.copy();
        users.add(copyToSave);
        saveToFile();

        return copyToSave;
    }

    @Override
    public void deleteByLogin(String login) {
        boolean removed = users.removeIf(u -> u.getLogin().equals(login));
        if (removed) {
            saveToFile();
        }
    }

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

                if (p.length >= 4) {
                    users.add(new User(p[0], p[1], Role.valueOf(p[2]), (p[3])));
                } else if (p.length == 3) {
                    users.add(new User(p[0], p[0], Role.valueOf(p[1]), p[2]));
                }
            }
        } catch (Exception e) {
            System.err.println("Błąd odczytu użytkowników: " + e.getMessage());
        }
    }
}