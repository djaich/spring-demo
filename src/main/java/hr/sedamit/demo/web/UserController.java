package hr.sedamit.demo.web;

import hr.sedamit.demo.model.User;
import hr.sedamit.demo.service.UserManager;
import hr.sedamit.demo.web.commands.UpdateUserCommand;
import hr.sedamit.demo.web.dto.DTOFactory;
import hr.sedamit.demo.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserManager userManager;

    @Value("${user.list.allowed:true}")
    private boolean allowedListUsers;

    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping(value = "/list")
    public List<UserDTO> listAllUsers() {

        if (allowedListUsers) {
            final List<User> allUsers = userManager.getAllUsers();
            return allUsers.stream()
                    .map(DTOFactory::toUserDTO)
                    .collect(Collectors.toList());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User list not allowed!");
        }
    }

    @GetMapping("/{userId}")
    public UserDTO showUserDetails(@PathVariable Long userId) {
        Optional<User> optionalUser = userManager.getUser(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return DTOFactory.toUserDTO(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with such id does not exist");
        }
    }

    @PostMapping("/add")
    public UserDTO addUser(@RequestBody UpdateUserCommand userData) {
        User user = new User();
        updateUserData(user, userData);
        return DTOFactory.toUserDTO(userManager.save(user));
    }

    @PutMapping("{userId}/update")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody UpdateUserCommand userData) {
        Optional<User> optionalUser = userManager.getUser(userId);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with such id.");
        }
        User user = optionalUser.get();
        updateUserData(user, userData);
        return DTOFactory.toUserDTO(userManager.save(user));
    }

    @DeleteMapping("/delete/{userId}")
    public Boolean deleteUser(@PathVariable Long userId) {
        try {
            userManager.delete(userId);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @PostConstruct
    public void init() {
        log.info("User controller ready");
    }

    private void updateUserData(User user, UpdateUserCommand command) {
        user.setUserName(command.getUserName());
        user.setFullName(command.getFullName());
        user.setPassword(command.getPassword());
        user.setActive(command.isActive());
        user.setAge(command.getAge());
    }

}
