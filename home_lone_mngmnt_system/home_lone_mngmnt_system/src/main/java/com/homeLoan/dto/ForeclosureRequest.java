package com.homeLoan.dto;


import javax.validation.constraints.NotNull;

public class ForeclosureRequest {

    @NotNull
    private Integer loanId;

    public ForeclosureRequest() {
    }

    public ForeclosureRequest(int loanId) {
        this.loanId = loanId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }
}