package com.example.otends.controller;

import com.example.otends.entities.User;
import com.example.otends.middleware.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/user")
@RestController
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void add(@RequestBody User user) {
        this.userService.add(user);
    }

    @PostMapping(path = "login")
    public User login(@RequestBody User user) {
        return this.userService.login(user.getEmail(), user.getPassword())
                .orElse(null);
    }

    @GetMapping
    public List<User> getAll() {
        return this.userService.getAll();
    }

    @GetMapping(path = "{id}")
    public User get(@PathVariable UUID id) {
        return this.userService.get(id)
                .orElse(null);
    }
}
