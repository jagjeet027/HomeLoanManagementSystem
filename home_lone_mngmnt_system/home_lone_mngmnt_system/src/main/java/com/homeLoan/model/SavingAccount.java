package com.homeLoan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class SavingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @NotNull
    private long accountNumber;
    private String name;
    private String email;
    private double balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LoanAccount> loans;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoanApplication> applications;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public SavingAccount() {
    }

    public SavingAccount(int accountId, long accountNumber, String name, String email, double balance, List<LoanAccount> loans, List<LoanApplication> applications, Users user) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.loans = loans;
        this.applications = applications;
        this.user = user;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<LoanAccount> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanAccount> loans) {
        this.loans = loans;
    }

    public List<LoanApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<LoanApplication> applications) {
        this.applications = applications;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
