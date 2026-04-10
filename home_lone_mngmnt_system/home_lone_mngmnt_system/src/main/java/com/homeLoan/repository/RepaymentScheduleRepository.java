package com.homeLoan.repository;

import com.homeLoan.model.RepaymentSchedule;
import com.homeLoan.status.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule, Integer> {
    List<RepaymentSchedule> findByLoanId(int loanId);
    List<RepaymentSchedule> findByStatus(PaymentStatus status);
}