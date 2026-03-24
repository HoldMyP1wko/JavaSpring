package org.example;

import org.apache.commons.codec.digest.DigestUtils;

public class Authentication {
    private IUserRepository userRepository;

    public Authentication(IUserRepository userRepository) {
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
}