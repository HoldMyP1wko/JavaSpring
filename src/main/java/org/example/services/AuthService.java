package org.example.services;

import org.example.models.Role;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String login, String password) {
        Optional<User> userOptional = userRepository.findByLogin(login);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public boolean register(String login, String password) {
        if (userRepository.findByLogin(login).isPresent()) {
            return false;
        }
        Role assignedRole = login.equalsIgnoreCase("admin") ? Role.ADMIN : Role.USER;

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User newUser = new User(login, hashedPassword, assignedRole, null);

        userRepository.save(newUser);
        return true;
    }
}