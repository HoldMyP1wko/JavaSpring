package org.example.models;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter private final String login;
    @Getter private final String password;
    @Getter private final Role role;
    @Setter
    private String rentedVehicleId;

    public User(String login, String password, Role role, String rentedVehicleId) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.rentedVehicleId = rentedVehicleId;
    }

    public User copy() {
        return new User(this.login, this.password, this.role, this.rentedVehicleId);
    }

    public String toCSV() {
        return login + ";" + password + ";" + role.name() + ";" + (rentedVehicleId != null ? rentedVehicleId : "");
    }
}