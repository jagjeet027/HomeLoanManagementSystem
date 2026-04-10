package com.homeLoan.dto;

public class LoanAccountResponse {

    private int loanId;
    private double loanAmount;
    private double emi;
    private int tenure;
    private String status;

    public LoanAccountResponse() {
    }

    public LoanAccountResponse(int loanId, double loanAmount, double emi, int tenure, String status) {
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.emi = emi;
        this.tenure = tenure;
        this.status = status;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}