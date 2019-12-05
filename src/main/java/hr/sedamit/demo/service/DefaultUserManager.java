package hr.sedamit.demo.service;

import hr.sedamit.demo.dao.UserRepository;
import hr.sedamit.demo.dao.UserSearchFilter;
import hr.sedamit.demo.dao.UserSpecifications;
import hr.sedamit.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        UserSearchFilter filter = new UserSearchFilter(Boolean.TRUE, 30);

        Specification<User> spec = UserSpecifications.findUsers(filter);
        return repository.findAll(spec);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findByUserName(username);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("No user with such name");
        }
    }
}
