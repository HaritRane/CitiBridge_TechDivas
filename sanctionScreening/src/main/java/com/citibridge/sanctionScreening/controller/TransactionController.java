package com.citibridge.sanctionScreening.controller;

import com.citibridge.sanctionScreening.repo.TransactionRepo;
import com.citibridge.sanctionScreening.response.ResponseMessage;
import com.citibridge.sanctionScreening.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepo transactionRepo;

    @PatchMapping("/{fileId}/validate")
    public ResponseEntity<ResponseMessage> validateTransactions(@PathVariable String fileId){
       String res= transactionService.validateTransactions(transactionRepo.getAllTransaction(fileId));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(res));
    }

}
