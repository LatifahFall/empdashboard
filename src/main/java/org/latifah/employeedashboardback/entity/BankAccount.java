package org.latifah.employeedashboardback.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "accounts")
public class BankAccount {
    @Id
    private String id;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = java.util.UUID.randomUUID().toString();
        }
    }

    // champ chiffré (invisible à l’extérieur)
    @Column(name = "accountNumber_encrypted")
    private String accountNumber;

    // champ lisible, utilisé pour l’affichage et la recherche
    @Column(name = "rawAccountNumber", unique = true)
    private String rawAccountNumber;

    private String type; // courant / épargne
    private double balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Transaction> transactions;

    // getters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
    public String getRawAccountNumber() {
        return rawAccountNumber;
    }
    public void setRawAccountNumber(String rawAccountNumber) {
        this.rawAccountNumber = rawAccountNumber;
    }
}
