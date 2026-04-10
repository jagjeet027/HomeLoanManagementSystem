package com.homeLoan.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.homeLoan.status.LoanStatus;

import javax.persistence.*;
import java.util.List;

@Entity
public class LoanAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double loanAmount;
    private double interestRate;
    private int tenure;
    private double emi;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;


    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private SavingAccount account;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RepaymentSchedule> schedules;

    public LoanAccount() {
    }

    public LoanAccount(int id, double loanAmount, double interestRate, int tenure, double emi, LoanStatus status, SavingAccount account, List<RepaymentSchedule> schedules) {
        this.id = id;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.tenure = tenure;
        this.emi = emi;
        this.status = status;
        this.account = account;
        this.schedules = schedules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public SavingAccount getAccount() {
        return account;
    }

    public void setAccount(SavingAccount account) {
        this.account = account;
    }

    public List<RepaymentSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<RepaymentSchedule> schedules) {
        this.schedules = schedules;
    }
}

