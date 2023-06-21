package com.citibridge.sanctionScreening.entity;

import jakarta.persistence.*;


@Entity
public class Transaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "payer-name")
private  String PayerName;

@Column(name = "payee-name")
    private String PayeeName;
    public Transaction() {
    }

    public Transaction( String payerName, String payeeName) {

        PayerName = payerName;
        PayeeName = payeeName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
