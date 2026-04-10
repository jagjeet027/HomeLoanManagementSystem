package com.homeLoan.services;

import com.homeLoan.exceptions.InvalidRequestException;
import com.homeLoan.model.Users;
import com.homeLoan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Users register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = userRepository.save(user);
        log.info("User saved with id: {}", savedUser.getUserId());
        return savedUser;
    }

    public Users login(String username, String password) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidRequestException("Invalid Username"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidRequestException("Invalid Password");
        }
        log.info("Login successful: {}", username);
        return user;
    }
}