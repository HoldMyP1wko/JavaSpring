package org.example;

public class User {
    private final String login;
    private final String password;
    private Role role;
    private String rentedVehicle = null;

    public Role getRole() {
        return role;
    }

    public User(String login, String password, Role role, String rentedVehicle) {
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

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setRentedVehicle(String rentedVehicle) {
        this.rentedVehicle = rentedVehicle;
    }

    public String getRentedVehicle() {
        return rentedVehicle;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", rentedVehicle='" + rentedVehicle+ '\'' +
                '}';
    }

}
