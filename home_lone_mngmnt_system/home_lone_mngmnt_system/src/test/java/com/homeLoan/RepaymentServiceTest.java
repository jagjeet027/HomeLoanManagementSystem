package com.homeLoan;

import com.homeLoan.exceptions.LoanException;
import com.homeLoan.model.LoanAccount;
import com.homeLoan.model.RepaymentSchedule;
import com.homeLoan.services.RepaymentService;
import com.homeLoan.status.LoanStatus;
import com.homeLoan.status.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RepaymentServiceTest {

    @InjectMocks
    private RepaymentService repaymentService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateEMI() {
        double emi = repaymentService.calculateEMI(100000, 7, 5);
        assertTrue(emi > 0);
    }


    @Test
    void testPayEmi_alreadyPaid() {
        RepaymentSchedule schedule = new RepaymentSchedule();
        schedule.setStatus(PaymentStatus.PAID);

        assertThrows(LoanException.class, () -> {
            repaymentService.payEmi(schedule);
        });
    }


    @Test
    void testForeclose_fail_lessEmi() {
        LoanAccount loan = new LoanAccount();
        loan.setStatus(LoanStatus.ONGOING);

        List<RepaymentSchedule> list = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            RepaymentSchedule s = new RepaymentSchedule();
            s.setStatus(PaymentStatus.PAID);
            list.add(s);
        }

        assertThrows(LoanException.class, () -> {
            repaymentService.forecloseLoan(loan, list);
        });
    }


    @Test
    void testPrepay_fail_smallAmount() {
        LoanAccount loan = new LoanAccount();
        loan.setEmi(2000);
        loan.setStatus(LoanStatus.ONGOING);

        assertThrows(LoanException.class, () -> {
            repaymentService.prepay(loan, 1000);
        });
    }
}