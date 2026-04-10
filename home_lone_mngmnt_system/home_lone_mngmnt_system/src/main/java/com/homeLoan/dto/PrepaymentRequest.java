package com.homeLoan.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PrepaymentRequest {
    @NotNull
    private Integer loanId;

    @NotNull
    @Positive
    private Double amount;

    public PrepaymentRequest() {
    }

    public PrepaymentRequest(int loanId, double amount) {
        this.loanId = loanId;
        this.amount = amount;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}