package com.homeLoan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String username;
    private String password;
    private String role;

    @OneToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private SavingAccount account;

    public Users() {
    }

    public Users(int userId, String username, String password, String role, SavingAccount account) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.account = account;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public SavingAccount getAccount() {
        return account;
    }

    public void setAccount(SavingAccount account) {
        this.account = account;
    }
}
