package com.citibridge.sanctionScreening.service;

import com.citibridge.sanctionScreening.entity.Transaction;
import com.citibridge.sanctionScreening.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private TransactionRepo transactionRepo;

    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Override
    public String validateTransactions(List<Transaction> transactions) {
int val_pass=0;
int val_fail=0;
        LocalDate currentDate=LocalDate.now();

         String DOUBLE_PATTERN = "^(?!0\\.0\\d{2})\\d{1,10}(\\.\\d{1,2})?$";
         Pattern pattern = Pattern.compile(DOUBLE_PATTERN);
        //List<Transaction> transactions=transactionRepo.findAll();
        for(Transaction transaction:transactions){
            boolean flag=true;
            if(transaction.getId().length()!=12){
            flag=false;
            }else if (transaction.getDate().isBefore(currentDate)){
                flag=false;
            }else if(transaction.getPayeeName().length()>35||transaction.getPayerName().length()>35){
                flag=false;
            }else if(transaction.getPayeeAccount().length()!=12 || transaction.getPayerAccount().length()!=12){
                flag=false;
            }else if(!(pattern.matcher(Double.toString(transaction.getAmount())).matches())){
                flag=false;
            }
            if(flag){
                transaction.setStatus("Validation-pass");
                val_pass++;

            }else{
                transaction.setStatus("Validation-fail");
              val_fail++;
            }


        }

        transactionRepo.saveAll(transactions);
        return "Validation pass"+val_pass+"/nValidation fail"+val_fail;

    }
}
