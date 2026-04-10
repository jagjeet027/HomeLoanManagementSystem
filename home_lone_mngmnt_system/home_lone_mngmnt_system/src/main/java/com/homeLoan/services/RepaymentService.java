package com.homeLoan.services;

import com.homeLoan.exceptions.LoanException;
import com.homeLoan.model.LoanAccount;
import com.homeLoan.model.RepaymentSchedule;
import com.homeLoan.repository.LoanAccountRepository;
import com.homeLoan.repository.RepaymentScheduleRepository;
import com.homeLoan.status.LoanStatus;
import com.homeLoan.status.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepaymentService {

    @Autowired
    private LoanAccountRepository loanRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RepaymentScheduleRepository scheduleRepository;

    public double calculateEMI(double P, double annualRate, int tenureYears) {
        double R = annualRate / 12 / 100;
        int N = tenureYears * 12;

        return (P * R * Math.pow(1 + R, N)) /
                (Math.pow(1 + R, N) - 1);
    }

    public List<RepaymentSchedule> generateSchedule(LoanAccount loan) {
        List<RepaymentSchedule> list = new ArrayList<>();
        double balance = loan.getLoanAmount();
        double emi = loan.getEmi();
        double R = loan.getInterestRate() / 12 / 100;

        int months = loan.getTenure() * 12;

        for (int i = 1; i <= months; i++) {
            double interest = balance * R;
            double principal = emi - interest;
            balance -= principal;

            RepaymentSchedule s = new RepaymentSchedule();
            s.setMonth(i);
            s.setEmi(emi);
            s.setInterest(interest);
            s.setPrincipal(principal);
            s.setOutstanding(balance);
            s.setStatus(PaymentStatus.PENDING);
            s.setLoan(loan);

            list.add(s);
        }
        return list;
    }

    public void payEmi(RepaymentSchedule schedule) {

        if (schedule.getStatus() == PaymentStatus.PAID) {
            throw new LoanException("Already paid");
        }

        schedule.setStatus(PaymentStatus.PAID);
        schedule.setPaidAmount(schedule.getEmi());
        LoanAccount loan = schedule.getLoan();

        double newOutstanding = loan.getLoanAmount() - schedule.getPrincipal();
        loan.setLoanAmount(newOutstanding);

        scheduleRepository.save(schedule);
        loanRepo.save(loan);

        emailService.sendEmail(
                loan.getAccount().getEmail(),
                "EMI Paid: " + schedule.getEmi() +
                        "\nRemaining Balance: " + loan.getLoanAmount()
        );
    }

    public void forecloseLoan(LoanAccount loan, List<RepaymentSchedule> schedules) {
        if (loan.getStatus() == LoanStatus.CLOSED) {
            throw new LoanException("Loan already closed");
        }
        int paidCount = (int) schedules.stream()
                .filter(s -> s.getStatus() == PaymentStatus.PAID)
                .count();

        if (paidCount < 3) {
            throw new LoanException("Foreclosure allowed after 3 EMIs");
        }

        for (RepaymentSchedule s : schedules) {
            if (s.getStatus() == PaymentStatus.PENDING) {
                s.setStatus(PaymentStatus.CANCELLED);
            }
        }

        loan.setLoanAmount(0);
        loan.setEmi(0);
        loan.setStatus(LoanStatus.CLOSED);

        scheduleRepository.saveAll(schedules);
        loanRepo.save(loan);

        emailService.sendEmail(
                loan.getAccount().getEmail(),
                "Loan Foreclosed Successfully. Loan Closed."
        );
    }

    public void prepay(LoanAccount loan, double amount) {

        if (loan.getStatus() != LoanStatus.ONGOING) {
            throw new LoanException("Loan is not active");
        }
        if (amount < loan.getEmi() * 3) {
            throw new LoanException("Minimum prepayment is 3 times EMI");
        }
        double newPrincipal = loan.getLoanAmount() - amount;
        if (newPrincipal <= 0) {
            throw new LoanException("Amount too large, use foreclosure instead");
        }
        List<RepaymentSchedule> oldSchedules =
                scheduleRepository.findByLoanId(loan.getId());
        long remainingMonths = oldSchedules.stream()
                .filter(s -> s.getStatus() == PaymentStatus.PENDING)
                .count();

        int remainingYears = (int) Math.ceil(remainingMonths / 12.0);
        for (RepaymentSchedule s : oldSchedules) {
            if (s.getStatus() == PaymentStatus.PENDING) {
                s.setStatus(PaymentStatus.CANCELLED);
            }
        }

        scheduleRepository.saveAll(oldSchedules);
        loan.setLoanAmount(newPrincipal);
        double newEmi = calculateEMI(newPrincipal, loan.getInterestRate(), remainingYears);
        loan.setEmi(newEmi);
        List<RepaymentSchedule> newSchedules = generateSchedule(loan);
        loan.setSchedules(newSchedules);

        loanRepo.save(loan);
        scheduleRepository.saveAll(newSchedules);

        emailService.sendEmail(
                loan.getAccount().getEmail(),
                "Prepayment Successful\nNew EMI: " + newEmi +
                        "\nRemaining Loan: " + newPrincipal
        );
    }


    public List<RepaymentSchedule> getSchedule(int loanId) {
        return scheduleRepository.findByLoanId(loanId);
    }

    public void payEmiById(int id) {
        RepaymentSchedule s = scheduleRepository.findById(id)
                .orElseThrow(() -> new LoanException("Schedule not found"));
        payEmi(s);
    }

    public void forecloseByLoanId(int loanId) {
        LoanAccount loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new LoanException("Loan not found"));
        List<RepaymentSchedule> list = scheduleRepository.findByLoanId(loanId);
        forecloseLoan(loan, list);
    }

    public void prepayLoan(int loanId, double amount) {
        LoanAccount loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new LoanException("Loan not found"));

        prepay(loan, amount);
    }

}