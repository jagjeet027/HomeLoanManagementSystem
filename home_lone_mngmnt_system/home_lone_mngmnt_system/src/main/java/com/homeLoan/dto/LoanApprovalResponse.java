package com.homeLoan.dto;

public class LoanApprovalResponse {

    private int applicationId;
    private double eligibleAmount;
    private boolean approved;
    private String message;

    public LoanApprovalResponse() {
    }

    public LoanApprovalResponse(int applicationId, double eligibleAmount, boolean approved, String message) {
        this.applicationId = applicationId;
        this.eligibleAmount = eligibleAmount;
        this.approved = approved;
        this.message = message;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public double getEligibleAmount() {
        return eligibleAmount;
    }

    public void setEligibleAmount(double eligibleAmount) {
        this.eligibleAmount = eligibleAmount;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}