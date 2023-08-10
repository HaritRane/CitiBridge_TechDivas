package com.citibridge.sanctionScreening.service;


import com.citibridge.sanctionScreening.entity.Keywords;
import com.citibridge.sanctionScreening.entity.Transaction;
import com.citibridge.sanctionScreening.repo.KeywordRepo;
import com.citibridge.sanctionScreening.repo.TransactionRepo;
import com.citibridge.sanctionScreening.response.ResponseCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TransactionService {
    @Autowired
    private final TransactionRepo transactionRepo;
    @Autowired
    private final KeywordRepo keywordRepo;



    public TransactionService(TransactionRepo transactionRepo, KeywordRepo keywordRepo) {
        this.transactionRepo = transactionRepo;
        this.keywordRepo = keywordRepo;
    }


    public int[] validateTransactions(List<Transaction> transactions) {
//        int val_pass = 0;
//        int val_fail = 0;
        int[] val_result=new int[2];
        LocalDate currentDate = LocalDate.now();

        String DOUBLE_PATTERN = "^(?!-)(?:\\d{1,7}(?:\\.\\d{1,2})?|\\.\\d{1,2})$";
        Pattern pattern = Pattern.compile(DOUBLE_PATTERN);
        boolean flag = true;
        //List<Transaction> transactions=transactionRepo.findAll();
        for (Transaction transaction : transactions) {
            //.out.println(Long.parseLong(transaction.getId()) );
            if (transaction.getId().length()!=12) {
                flag = false;

                System.out.println(transaction.getId());
            } else if (transaction.getDate().isBefore(currentDate) || transaction.getDate().isAfter(currentDate)) {
                flag = false;
                System.out.println(transaction.getDate());
            } else if (transaction.getPayeeName().length() > 35 || transaction.getPayerName().length() > 35 ||transaction.getPayerName().split("\\s+").length<2 ||transaction.getPayeeName().split("\\s+").length<2) {
                flag = false;
                System.out.println(transaction.getPayeeName().length() + transaction.getPayerName().length());
            } else if (transaction.getPayeeAccount().length() != 12 || transaction.getPayerAccount().length() != 12) {
                flag = false;
                System.out.println(transaction.getPayeeAccount().length() + transaction.getPayerAccount().length());
            } else if (!(pattern.matcher(Double.toString(transaction.getAmount())).matches())) {
                flag = false;
                System.out.println(transaction.getAmount());
            }

            if (flag) {
                transaction.setStatus("Validation-pass");
               // val_pass++;
                val_result[0]++;

            } else {
                transaction.setStatus("Validation-fail");
               // val_fail++;
                val_result[1]++;
                flag=true;
            }


        }

        transactionRepo.saveAll(transactions);
        return val_result;

    }


    public int[] screenTransactions(long fileId) {
//        int screening_pass=0;
//        int screening_fail=0;
        int[] screen_result=new int[2];
        List<Transaction> transactions=transactionRepo.getValidTransaction(fileId);
        for(Transaction transaction: transactions){
            String payeeName=transaction.getPayeeName();
            String payerName=transaction.getPayerName();
            if(keywordRepo.findNameCount(payeeName)>0 ){
                keywordRepo.findByName(payeeName).occured();
                transaction.setStatus("Screening-fail");
                // screening_fail++;
                screen_result[1]++;
            }
                   else if(keywordRepo.findNameCount(payerName)>0){
                keywordRepo.findByName(payerName).occured();
                transaction.setStatus("Screening-fail");
               // screening_fail++;
                screen_result[1]++;
            }else{
                transaction.setStatus("Screening-pass");

               // screening_pass++;
                screen_result[0]++;
            }
        }
        transactionRepo.saveAll(transactions);
        return screen_result;
    }


}
