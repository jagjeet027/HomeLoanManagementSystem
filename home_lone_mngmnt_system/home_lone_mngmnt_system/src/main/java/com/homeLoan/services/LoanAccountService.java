package com.homeLoan.services;

import com.homeLoan.exceptions.ResourceNotFoundException;
import com.homeLoan.model.LoanAccount;
import com.homeLoan.repository.LoanAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanAccountService {

    @Autowired
    private LoanAccountRepository loanRepository;

    public LoanAccount getLoan(int id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
    }
    public LoanAccount getLoanByAccountId(int accountId) {
        return loanRepository.findByAccountAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
    }
}