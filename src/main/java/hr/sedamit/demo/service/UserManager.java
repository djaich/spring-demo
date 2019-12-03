package hr.sedamit.demo.service;

import hr.sedamit.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserManager {

    List<User> getAllUsers();

    Optional<User> getUser(Long userId);

    User save(User user);

    void delete(Long userId);
}
