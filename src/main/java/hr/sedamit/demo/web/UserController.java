package hr.sedamit.demo.web;

import hr.sedamit.demo.model.User;
import hr.sedamit.demo.service.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

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
    public List<User> listAllUsers() {

        if (allowedListUsers) {
            return userManager.getAllUsers();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User list not allowed!");
        }
    }

    @GetMapping("/{userId}")
    public User showUserDetails(@PathVariable Long userId) {
        Optional<User> optionalUser = userManager.getUser(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with such id does not exist");
        }
    }

    @PutMapping("/save")
    public User saveUser(@RequestBody User user) {
        return userManager.save(user);
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


}
