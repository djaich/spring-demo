package hr.sedamit.demo.service;

import hr.sedamit.demo.dao.RoleRepository;
import hr.sedamit.demo.model.Role;
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
public class DefaultRoleManager implements RoleManager {

    private RoleRepository repository;

    public DefaultRoleManager(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Role> getAllRoles() {
        log.info("Listing roles: " + repository.findAll());

        return repository.findAll();
    }

    @Override
    public Optional<Role> getRole(Long roleId) {
        return repository.findById(roleId);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public void delete(Long roleId) {
        repository.deleteById(roleId);

    }

    @PostConstruct
    public void init() {
        log.info("Default role manager ready");
    }

}
