package org.example;

public class User {
    private String login;
    private String password; // Hash
    private Role role;
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

    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public String getRentedVehicleId() { return rentedVehicleId; }
    public void setRentedVehicleId(String rentedVehicleId) { this.rentedVehicleId = rentedVehicleId; }
}