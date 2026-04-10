package com.homeLoan.repository;

import com.homeLoan.model.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccount, Integer> {
    Optional<SavingAccount> findByUserUserId(int userId);
}
