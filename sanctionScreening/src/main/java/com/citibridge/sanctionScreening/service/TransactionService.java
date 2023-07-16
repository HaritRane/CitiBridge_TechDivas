package com.citibridge.sanctionScreening.service;

import com.citibridge.sanctionScreening.entity.Transaction;

import java.util.List;

public interface TransactionService {
    String validateTransactions(List<Transaction> transactions);
    String screenTransactions(List<Transaction> transactions);
}
