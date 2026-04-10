package com.homeLoan.repository;

import com.homeLoan.model.LoanAccount;
import com.homeLoan.status.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, Integer> {
    List<LoanAccount> findByStatus(LoanStatus status);

    boolean existsByAccountAccountIdAndStatus(int accountId, LoanStatus status);

    Optional<LoanAccount> findByAccountAccountId(int accountId);
}