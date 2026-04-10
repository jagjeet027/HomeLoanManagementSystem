package com.homeLoan.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homeLoan.status.PaymentStatus;

import javax.persistence.*;

@Entity
public class RepaymentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int month;
    private double emi;
    private double principal;
    private double interest;
    private double outstanding;
    private double paidAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    @JsonBackReference
    private LoanAccount loan;

    public RepaymentSchedule() {
    }

    public RepaymentSchedule(int id, int month, double emi, double principal, double interest, double outstanding, double paidAmount, PaymentStatus status, LoanAccount loan) {
        this.id = id;
        this.month = month;
        this.emi = emi;
        this.principal = principal;
        this.interest = interest;
        this.outstanding = outstanding;
        this.paidAmount = paidAmount;
        this.status = status;
        this.loan = loan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(double outstanding) {
        this.outstanding = outstanding;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LoanAccount getLoan() {
        return loan;
    }

    public void setLoan(LoanAccount loan) {
        this.loan = loan;
    }
}
