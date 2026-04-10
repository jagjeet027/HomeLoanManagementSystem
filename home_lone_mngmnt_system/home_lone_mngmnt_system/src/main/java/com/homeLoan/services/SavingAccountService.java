package com.homeLoan.services;

import com.homeLoan.exceptions.InvalidRequestException;
import com.homeLoan.model.SavingAccount;
import com.homeLoan.model.Users;
import com.homeLoan.repository.SavingAccountRepository;
import com.homeLoan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SavingAccountService {

    @Autowired
    private SavingAccountRepository accountRepo;

    @Autowired
    private UserRepository userRepo;

    public SavingAccount createAccount(int userId, SavingAccount account) {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new InvalidRequestException("User not found"));

        Optional<SavingAccount> existing = accountRepo.findByUserUserId(userId);
        if (existing.isPresent()) {
            throw new InvalidRequestException("Account already exists for this user");
        }
        account.setUser(user);
        account.setBalance(0);
        return accountRepo.save(account);
    }




    public SavingAccount getAccountByUser(int userId) {
        return accountRepo.findByUserUserId(userId)
                .orElseThrow(() -> new InvalidRequestException("No account found, please create one"));
    }



    public SavingAccount getAccount(int id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new InvalidRequestException("Account not found"));
    }
}