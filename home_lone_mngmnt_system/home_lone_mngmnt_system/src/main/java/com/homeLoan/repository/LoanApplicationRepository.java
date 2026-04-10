package com.homeLoan.repository;

import com.homeLoan.model.LoanApplication;
import com.homeLoan.status.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Integer> {
    List<LoanApplication> findByStatus(ApplicationStatus status);
}
