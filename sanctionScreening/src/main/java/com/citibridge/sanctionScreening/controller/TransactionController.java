package com.citibridge.sanctionScreening.controller;

import com.citibridge.sanctionScreening.entity.Transaction;
import com.citibridge.sanctionScreening.repo.FileRepo;
import com.citibridge.sanctionScreening.repo.TransactionRepo;
import com.citibridge.sanctionScreening.response.ResponseMessage;
import com.citibridge.sanctionScreening.service.TransactionService;
import com.citibridge.sanctionScreening.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepo transactionRepo;
@Autowired
    FileRepo fileRepo;
    @PatchMapping("/{fileName}/validate")
    public ResponseEntity<ResponseMessage> validateTransactions(@PathVariable String fileName) {
        String res = transactionService.validateTransactions(transactionRepo.getAllTransaction(fileRepo.findByFileName(fileName).getFileId()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(res));
    }

    @PatchMapping("/{fileName}/screen")
    public ResponseEntity<ResponseMessage> screenTransactions(@PathVariable String fileName){
        String res = transactionService.screenTransactions(fileRepo.findByFileName(fileName).getFileId());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(res));
    }

    @GetMapping("/{fileName}/get-all-transactions")
    public List<Transaction> getAllTransactions(@PathVariable String fileName){
        return transactionRepo.getAllTransaction(fileRepo.findByFileName(fileName).getFileId());
    }

    @GetMapping("/{fileName}/get-valid-pass-transactions")
    public List<Transaction> getValidPassTransactions(@PathVariable String fileName){
        return transactionRepo.getValidTransaction(fileRepo.findByFileName(fileName).getFileId());
    }

    @GetMapping("/{fileName}/get-valid-fail-transactions")
    public List<Transaction> getValidFailTransactions(@PathVariable String fileName){
        return transactionRepo.getInvalidTransaction(fileRepo.findByFileName(fileName).getFileId());
    }
    @GetMapping("/{fileName}/get-screen-fail-transactions")
    public List<Transaction> getScreenFailTransactions(@PathVariable String fileName){
        return transactionRepo.getScreenFailTransaction(fileRepo.findByFileName(fileName).getFileId());
    }
    @GetMapping("/{fileName}/get-screen-pass-transactions")
    public List<Transaction> getScreenPassTransactions(@PathVariable String fileName){
        return transactionRepo.getScreenPassTransaction(fileRepo.findByFileName(fileName).getFileId());
    }

    @PatchMapping("/{fileName}/findResult")
    public ResponseEntity<ResponseMessage> findResult(@PathVariable String fileName) {
        String res = transactionService.validateTransactions(transactionRepo.getAllTransaction(fileRepo.findByFileName(fileName).getFileId())) + transactionService.screenTransactions(fileRepo.findByFileName(fileName).getFileId());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(res));
    }
}
