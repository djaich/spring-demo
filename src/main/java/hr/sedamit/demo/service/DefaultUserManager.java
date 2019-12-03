package hr.sedamit.demo.service;

import hr.sedamit.demo.dao.UserRepository;
import hr.sedamit.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Profile("!test")
public class DefaultUserManager implements UserManager {

    private UserRepository repository;

    public DefaultUserManager(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Listing users: " + repository.findAll());
        return repository.findAll();
    }

    @Override
    public Optional<User> getUser(Long userId) {
        return repository.findById(userId);
    }

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(Long userId) {
        repository.deleteById(userId);

    }

    @PostConstruct
    public void init() {
        log.info("Default user manager ready");
    }
}
