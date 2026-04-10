package com.homeLoan.controller;

import com.homeLoan.dto.LoginRequest;
import com.homeLoan.model.Users;
import com.homeLoan.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173"})
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        Users savedUser = service.register(user);
        log.info("User registered with id: {}", savedUser.getUserId());
        return savedUser;
    }

    @PostMapping("/login")
    public Users login(@RequestBody LoginRequest request) {
        Users user = service.login(request.getUsername(), request.getPassword());
        log.info("Login success for username: {}", user.getUsername());
        return user;
    }
}