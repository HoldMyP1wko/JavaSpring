package org.example;

import java.util.List;


public interface IUserRepository {
    User getUser(String login);
    List<User> getUsers();
    void update(User user);
    void addUser(User user);
    boolean removeUser(String login);
}