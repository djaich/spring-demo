package hr.sedamit.demo.web;

import hr.sedamit.demo.model.Role;
import hr.sedamit.demo.service.RoleManager;
import hr.sedamit.demo.web.dto.DTOFactory;
import hr.sedamit.demo.web.dto.RoleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    private RoleManager roleManager;


    public RoleController(RoleManager roleManager, PasswordEncoder passwordEncoder) {
        this.roleManager = roleManager;
    }

    @GetMapping(value = "/list")
    public List<RoleDTO> listAllRoles() {

        final List<Role> allRoles = roleManager.getAllRoles();
        return allRoles.stream()
                .map(DTOFactory::toRoleDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/{roleId}")
    public RoleDTO showRoleDetails(@PathVariable Long roleId) {
        Optional<Role> optionalRole = roleManager.getRole(roleId);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            return DTOFactory.toRoleDTO(role);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with such id does not exist");
        }
    }

    @PostMapping("/add")
    public RoleDTO addRole(@RequestBody RoleDTO roleData) {
        Role role = new Role();
        updateRoleData(role, roleData, true);
        return DTOFactory.toRoleDTO(roleManager.save(role));
    }

    @PutMapping("/{roleId}/update")
    public RoleDTO updateRole(@PathVariable Long roleId, @RequestBody RoleDTO roleData) {
        Optional<Role> optionalRole = roleManager.getRole(roleId);
        if (!optionalRole.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No role with such id.");
        }
        Role role = optionalRole.get();
        updateRoleData(role, roleData, false);
        return DTOFactory.toRoleDTO(roleManager.save(role));
    }

    @DeleteMapping("/delete/{roleId}")
    public Boolean deleteRole(@PathVariable Long roleId) {
        try {
            roleManager.delete(roleId);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @PostConstruct
    public void init() {
        log.info("Role controller ready");
    }

    private void updateRoleData(Role role, RoleDTO command, boolean isNew) {
        role.setName(command.getName());
        role.setPermissions(command.getPermissions());
    }

}
