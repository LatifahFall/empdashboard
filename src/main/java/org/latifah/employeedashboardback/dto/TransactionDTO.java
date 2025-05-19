package org.latifah.employeedashboardback.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long transactionId;
    private double amount;
    private LocalDate date;

    // constructeur, getters/setters
    public TransactionDTO() {
        super();
    }
    public TransactionDTO(Long transactionId, double amount, LocalDate date) {
        super();
    }

    public TransactionDTO(String id, double amount, LocalDateTime date) {
    }

    public Long getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
