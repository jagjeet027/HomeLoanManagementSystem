package com.homeLoan.services;

import com.homeLoan.dto.LoanApplicationRequest;
import com.homeLoan.exceptions.ResourceNotFoundException;
import com.homeLoan.model.*;
import com.homeLoan.repository.*;
import com.homeLoan.status.ApplicationStatus;
import com.homeLoan.status.LoanStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoanApplicationService {
    private static final Logger log = LoggerFactory.getLogger(LoanApplicationService.class);

    @Autowired
    private LoanApplicationRepository applicationRepository;

    @Autowired
    private SavingAccountRepository accountRepository;

    @Autowired
    private LoanAccountRepository loanAccountRepository;

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private EmailService emailService;

    public LoanApplication applyLoan(LoanApplicationRequest request, SavingAccount account) {
        boolean hasActiveLoan = loanAccountRepository
                .existsByAccountAccountIdAndStatus(account.getAccountId(), LoanStatus.ONGOING);

        if (hasActiveLoan) {
            throw new RuntimeException("User already has an active loan");
        }

        double eligibleAmount = request.getSalary() * 50;

        LoanApplication application = new LoanApplication();
        application.setAccount(account);
        application.setRequestedAmount(request.getRequestedAmount());
        application.setEligibleAmount(eligibleAmount);
        application.setTenure(request.getTenure());
        application.setSalary(request.getSalary());
        application.setPropertyDetails(request.getPropertyDetails());
        application.setDocumentPath(request.getDocumentPath());

        if (request.getRequestedAmount() <= eligibleAmount) {
            application.setStatus(ApplicationStatus.APPROVED);

            LoanAccount loan = createLoanAccount(application, request.getRequestedAmount());
            loanAccountRepository.save(loan);
        } else {
            application.setStatus(ApplicationStatus.PENDING);
        }
        return applicationRepository.save(application);
    }

    public LoanAccount acceptEligibleAmount(int applicationId) {

        LoanApplication app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        LoanAccount loan = createLoanAccount(app, app.getEligibleAmount());

        app.setStatus(ApplicationStatus.APPROVED);
        applicationRepository.save(app);
        return loanAccountRepository.save(loan);
    }

    public void rejectApplication(int id) {
        LoanApplication app = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        app.setStatus(ApplicationStatus.REJECTED);
        applicationRepository.save(app);
    }


    private LoanAccount createLoanAccount(LoanApplication app, double amount) {

        LoanAccount loan = new LoanAccount();
        loan.setAccount(app.getAccount());
        loan.setLoanAmount(amount);
        loan.setInterestRate(7);
        loan.setTenure(app.getTenure());
        loan.setStatus(LoanStatus.ONGOING);

        double emi = repaymentService.calculateEMI(amount, 7, app.getTenure());
        loan.setEmi(emi);

        loan.setSchedules(repaymentService.generateSchedule(loan));

        emailService.sendEmail(
                app.getAccount().getEmail(),
                "Loan Approved!\nAmount: " + amount + "\nEMI: " + loan.getEmi()
        );
        return loan;
    }

    public List<LoanApplication> getAllApplications() {
        return applicationRepository.findAll();
    }
}