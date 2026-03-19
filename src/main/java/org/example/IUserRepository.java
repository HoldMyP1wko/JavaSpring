package org.example;

import java.util.List;

public interface IUserRepository {
    public User getUser(User user);
    public List<User> getUsers();
    public void save();
    public void load();
    public void update(User user, String rentedVehicle);


}
