package com.homeLoan.model;

import com.homeLoan.status.ApplicationStatus;
import javax.persistence.*;

@Entity
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double requestedAmount;
    private double eligibleAmount;
    private int tenure;
    private double salary;

    private String propertyDetails;
    private String documentPath;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    // MANY APPLICATIONS → ONE ACCOUNT
    @ManyToOne
    @JoinColumn(name = "account_id")
    private SavingAccount account;

    public LoanApplication() {
    }

    public LoanApplication(int id, double requestedAmount, double eligibleAmount, int tenure, double salary, String propertyDetails, String documentPath, ApplicationStatus status, SavingAccount account) {
        this.id = id;
        this.requestedAmount = requestedAmount;
        this.eligibleAmount = eligibleAmount;
        this.tenure = tenure;
        this.salary = salary;
        this.propertyDetails = propertyDetails;
        this.documentPath = documentPath;
        this.status = status;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public double getEligibleAmount() {
        return eligibleAmount;
    }

    public void setEligibleAmount(double eligibleAmount) {
        this.eligibleAmount = eligibleAmount;
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

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public SavingAccount getAccount() {
        return account;
    }

    public void setAccount(SavingAccount account) {
        this.account = account;
    }
}
