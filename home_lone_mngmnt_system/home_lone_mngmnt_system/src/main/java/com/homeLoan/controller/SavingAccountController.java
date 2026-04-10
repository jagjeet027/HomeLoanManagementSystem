package com.homeLoan.controller;

import com.homeLoan.model.SavingAccount;
import com.homeLoan.services.SavingAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = {"http://localhost:5173"})
public class SavingAccountController {

    private static final Logger logger = LoggerFactory.getLogger(SavingAccountController.class);

    @Autowired
    private SavingAccountService accountService;

    @PostMapping("/create/{userId}")
    public SavingAccount createAccount(@PathVariable int userId, @Valid @RequestBody SavingAccount account) {
        logger.info("Creating account for user id: {}", userId);
        return accountService.createAccount(userId, account);
    }

    @GetMapping("/user/{userId}")
    public SavingAccount getByUser(@PathVariable int userId) {
        logger.info("Fetching account for user id: {}", userId);
        return accountService.getAccountByUser(userId);
    }

    @GetMapping("/{id}")
    public SavingAccount getById(@PathVariable int id) {
        logger.info("Fetching account id: {}", id);
        return accountService.getAccount(id);
    }
}