package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private List<User> users = new ArrayList<>();
    private final String FILE_NAME = "users.csv";

    public UserRepository() {
        load();
    }

    @Override
    public User getUser(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equals(login))
                .map(User::copy)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsers() {
        List<User> copyList = new ArrayList<>();
        for (User u : users) {
            copyList.add(u.copy());
        }
        return copyList;
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLogin().equals(user.getLogin())) {
                users.set(i, user.copy());
                save();
                return;
            }
        }
    }

    @Override
    public void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User u : users) {
                pw.println(u.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Błąd zapisu użytkowników: " + e.getMessage());
        }
    }

    @Override
    public void load() {
        users.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";", -1);
                String rentedId = p[3].isEmpty() ? null : p[3];
                users.add(new User(p[0], p[1], Role.valueOf(p[2]), rentedId));
            }
        } catch (Exception e) {
            System.err.println("Błąd odczytu użytkowników: " + e.getMessage());
        }
    }
}