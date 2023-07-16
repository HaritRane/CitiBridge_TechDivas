package com.citibridge.sanctionScreening.service;

import com.citibridge.sanctionScreening.entity.Transaction;
import com.citibridge.sanctionScreening.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private final TransactionRepo transactionRepo;

    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Override
    public String validateTransactions(List<Transaction> transactions) {
        int val_pass = 0;
        int val_fail = 0;
        LocalDate currentDate = LocalDate.now();

        String DOUBLE_PATTERN = "^(?!-)(?:\\d{1,7}(?:\\.\\d{1,2})?|\\.\\d{1,2})$";
        Pattern pattern = Pattern.compile(DOUBLE_PATTERN);
        boolean flag = true;
        //List<Transaction> transactions=transactionRepo.findAll();
        for (Transaction transaction : transactions) {
            //.out.println(Long.parseLong(transaction.getId()) );
            if (transaction.getId().length()!=12) {
                flag = false;


            } else if (transaction.getDate().isBefore(currentDate)) {
                flag = false;

            } else if (transaction.getPayeeName().length() > 35 || transaction.getPayerName().length() > 35) {
                flag = false;

            } else if (transaction.getPayeeAccount().length() != 12 || transaction.getPayerAccount().length() != 12) {
                flag = false;

            } else if (!(pattern.matcher(Double.toString(transaction.getAmount())).matches())) {
                flag = false;

            }

            if (flag) {
                transaction.setStatus("Validation-pass");
                val_pass++;

            } else {
                transaction.setStatus("Validation-fail");
                val_fail++;
                flag=true;
            }


        }

        transactionRepo.saveAll(transactions);
        return "Validation pass: " + val_pass + "\n"+"Validation fail: " + val_fail;

    }

    @Override
    public String screenTransactions(List<Transaction> transactions) {
        return null;
    }
}
