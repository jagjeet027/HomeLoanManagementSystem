package com.homeLoan.dto;

import javax.validation.constraints.*;

public class LoanApplicationRequest {

    @NotNull(message = "Requested amount is required")
    private Double requestedAmount;

    @NotNull(message = "Tenure is required")
    @Min(value = 2, message = "Minimum tenure is 2 years")
    @Max(value = 20, message = "Maximum tenure is 20 years")
    private Integer tenure;

    @NotNull(message = "Salary is required")
    private Double salary;

    @NotBlank(message = "Property details required")
    private String propertyDetails;

    @NotBlank(message = "Document path required")
    private String documentPath;
    public LoanApplicationRequest() {
    }

    public LoanApplicationRequest( double requestedAmount, int tenure, double salary, String propertyDetails, String documentPath) {
        this.requestedAmount = requestedAmount;
        this.tenure = tenure;
        this.salary = salary;
        this.propertyDetails = propertyDetails;
        this.documentPath = documentPath;
    }


    public double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPropertyDetails() {
        return propertyDetails;
    }

    public void setPropertyDetails(String propertyDetails) {
        this.propertyDetails = propertyDetails;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}