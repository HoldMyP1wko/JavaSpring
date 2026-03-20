package org.example;

import org.apache.commons.codec.digest.Crypt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final List<User> usersList = new ArrayList<>();

    public UserRepository(){
        load();
    }

    @Override
    public User getUser(User user) {
        return new User(user);
    }

    @Override
    public List<User> getUsers() {
        List<User> copiedUsers = new ArrayList<>();

            for (User u : usersList){
                copiedUsers.add(new User(u));
            }
            return copiedUsers;
    }

    @Override
    public void save() {
        try (BufferedWriter br = new BufferedWriter(new FileWriter("src/main/resources/users.csv", true))) {
            for (User u : usersList) {
                br.write(u.getLogin() + ";");
                br.write(Crypt.crypt(u.getPassword() + ";"));
                br.write(u.getRole() + ";");
                if (u.getRentedVehicle() != null){
                    br.write(u.getRentedVehicle() + ";");
                }
                br.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void load() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/users.csv"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(";", -1);
                usersList.add(new User(parts[0], parts[1], Role.toEnum(parts[2]),parts[3]));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user,String rentedVehicle) {
        User edit = getUser(user);
        if (usersList.contains(edit)){
            user.setRentedVehicle(rentedVehicle);
            save();
        }
    }
}
