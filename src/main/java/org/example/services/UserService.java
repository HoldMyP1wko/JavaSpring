package org.example.services;

import org.example.models.User;
import org.example.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private UserRepository userRepo;
    private RentalService rentalService;

    public UserService(UserRepository userRepo, RentalService rentalRepo) {
        this.userRepo = userRepo;
        this.rentalService = rentalRepo;
    }

    public boolean removeUser(String login) {
        Optional<User> userOptional = userRepo.findByLogin(login);

        if (userOptional.isEmpty()){
            System.out.println("Taki użytkownik nie istnieje");
            return false;
        }

        if (rentalService.hasActiveRentals(login)) {
            System.out.println("Nie można usunąć użytkownika który wypożycza pojazd");
            return false;
        }

        User userToDelete = userOptional.get();
        userRepo.deleteByLogin(userToDelete.getLogin());

        System.out.println("Sukces: Użytkownik '" + login + "' został trwale usunięty.");
        return true;
    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
}