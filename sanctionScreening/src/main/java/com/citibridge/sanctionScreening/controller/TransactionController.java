package com.citibridge.sanctionScreening.controller;

import com.citibridge.sanctionScreening.entity.Transaction;
import com.citibridge.sanctionScreening.repo.FileRepo;
import com.citibridge.sanctionScreening.repo.TransactionRepo;
import com.citibridge.sanctionScreening.response.ResponseCount;
import com.citibridge.sanctionScreening.response.ResponseMessage;
import com.citibridge.sanctionScreening.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    LocalDate currentDate = LocalDate.now();
    @PatchMapping("/{fileName}/validate")
    public int[] validateTransactions(@PathVariable String fileName) {
        int[] res = transactionService.validateTransactions(transactionRepo.getAllTransaction(fileRepo.findByFileName(fileName).getFileId()));
        return res;
    }

    @PatchMapping("/{fileName}/screen")
    public int[] screenTransactions(@PathVariable String fileName){
        int[] res = transactionService.screenTransactions(fileRepo.findByFileName(fileName).getFileId());
        return res;
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
    public ResponseEntity<ResponseCount> findResult(@PathVariable String fileName) {
        int[] val = transactionService.validateTransactions(transactionRepo.getAllTransaction(fileRepo.findByFileName(fileName).getFileId())) ;
        int[] screen= transactionService.screenTransactions(fileRepo.findByFileName(fileName).getFileId());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseCount(val[0],val[1],screen[0],screen[1]));
    }

    @GetMapping("/all-count-today")
    public long getAllCount(){
        return transactionRepo.countByDate(currentDate);
    }
    @GetMapping("/valid-fail-count-today")
    public long getValidFailCountToday(){
        return transactionRepo.countByStatusAndDate("Validation-fail",currentDate);
    }
    @GetMapping("/screen-pass-count-today")
    public long getScreenPassCountToday(){
        return transactionRepo.countByStatusAndDate("Screening-pass",currentDate);
    }
    @GetMapping("/screen-fail-count-today")
    public long getScreenFailCountToday(){
        return transactionRepo.countByStatusAndDate("Screening-fail",currentDate);
    }
    @GetMapping("/all-count")
    public long getAllCountToday(){
        return transactionRepo.count();
    }
    @GetMapping("/valid-fail-count")
    public long getValidFailCount(){
        return transactionRepo.countByStatus("Validation-fail");
    }
    @GetMapping("/screen-pass-count")
    public long getScreenPassCount(){
        return transactionRepo.countByStatus("Screening-pass");
    }
    @GetMapping("/screen-fail-count")
    public long getScreenFailCount(){
        return transactionRepo.countByStatus("Screening-fail");
    }
}
