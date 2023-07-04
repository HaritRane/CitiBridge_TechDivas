package com.citibridge.sanctionScreening.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;


@Entity
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    private String id;

    private String fileId;

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

    public Transaction(String id, String fileId, LocalDate date, String payerName, String payerAccount, String payeeName, String payeeAccount, double amount) {
        this.id = id;
        this.fileId = fileId;
        this.date = date;
        PayerName = payerName;
        PayerAccount = payerAccount;
        PayeeName = payeeName;
        PayeeAccount = payeeAccount;
        this.amount = amount;

    }
}
