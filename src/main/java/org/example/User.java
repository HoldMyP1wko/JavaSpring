package org.example;

import org.apache.commons.codec.digest.Crypt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class User {
    String login;
    String password;
    String role;
    private String rentedVehicle;

    public User(String login, String password, String role, String rentedVehicle) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.rentedVehicle = rentedVehicle;
    }

    public User(User user){
        this.login = user.login;
        this.password = user.password;
        this.role = user.role;
        this.rentedVehicle = user.rentedVehicle;
    }

    public void setRentedVehicle(String rentedVehicle) {
        this.rentedVehicle = rentedVehicle;
    }

    public String getRentedVehicle() {
        return rentedVehicle;
    }

    static public boolean login(String login, String password){
        UserRepository u = new UserRepository();
        List<User> uUsersList = u.getUsers();
        if (uUsersList.equals(u)){

        }
    }

    static private void rentVehicle(User user){

    }


}
