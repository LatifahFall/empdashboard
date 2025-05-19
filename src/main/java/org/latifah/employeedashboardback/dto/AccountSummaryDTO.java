package org.latifah.employeedashboardback.dto;

import java.util.List;

public class AccountSummaryDTO {
    private String accountNumber;
    private String type;
    private double balance;
    private List<TransactionDTO> transactions;

    // constructeur, getters/setters
    public AccountSummaryDTO() {
        super();
    }
    public AccountSummaryDTO(String accountNumber, String type, double balance, List<TransactionDTO> transactions) {
        super();
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public List<TransactionDTO> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}
