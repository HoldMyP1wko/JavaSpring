package org.example.repositories.impl;

import org.example.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements org.example.repositories.UserRepository {
    private List<User> users = new ArrayList<>();
    

    @Override
    public List<User> findAll() {
    }

    @Override
    public Optional<User> findById(String id) {
    }

    @Override
    public Optional<User> findByLogin(String login) {
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}