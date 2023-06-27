package com.citibridge.sanctionScreening.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;


@Entity
public class Transaction {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "date",nullable = false)
    private LocalDate date;

    @Column(name = "payer-name",nullable = false)
private  String PayerName;

    @Column(name = "payer-account",nullable = false)
    private String PayerAccount;

@Column(name = "payee-name",nullable = false)
    private String PayeeName;

    @Column(name = "payee-account",nullable = false)
    private String PayeeAccount;

    @Column(name = "amount",nullable = false)
    private double amount;

@Column(name="status")
private String status;

    public String  isStatus() {
        return status;
    }

    public void setStatus(String  status) {
        this.status = status;
    }

    public Transaction() {
    }

    public Transaction(String id, LocalDate date, String payerName, String payerAccount, String payeeName, String payeeAccount,double amount) {
        this.id = id;
        this.date = date;
        PayerName = payerName;
        PayerAccount = payerAccount;
        PayeeName = payeeName;
        PayeeAccount = payeeAccount;
        amount=amount;

    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPayerAccount() {
        return PayerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        PayerAccount = payerAccount;
    }

    public String getPayeeAccount() {
        return PayeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        PayeeAccount = payeeAccount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String  getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getPayerName() {
        return PayerName;
    }

    public void setPayerName(String payerName) {
        PayerName = payerName;
    }

    public String getPayeeName() {
        return PayeeName;
    }

    public void setPayeeName(String payeeName) {
        PayeeName = payeeName;
    }
}
