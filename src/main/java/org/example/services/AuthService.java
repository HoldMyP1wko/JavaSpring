package org.example.services;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.models.Role;
import org.example.models.User;
import org.example.repositories.UserRepository;

public class AuthService {
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String login, String password) {
        User user = userRepository.getUser(login);
        if (user != null) {
            String hashedInputPassword = DigestUtils.sha256Hex(password);
            if (user.getPassword().equals(hashedInputPassword)) {
                return user;
            }
        }
        return null;
    }

    public boolean register(String login, String password) {
        if (userRepository.getUser(login) != null) {
            return false;
        }

        String hashedPassword = DigestUtils.sha256Hex(password);
        User newUser = new User(login, hashedPassword, Role.USER, null);

        userRepository.addUser(newUser);
        return true;
    }
}