package ua.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.challenge.entity.User;
import ua.challenge.service.UserService;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public void createUser() {
        this.userService.create();
    }

    @GetMapping("/users/list")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }
}
