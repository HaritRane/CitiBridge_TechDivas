package com.citibridge.sanctionScreening.service;

import com.citibridge.sanctionScreening.entity.Transaction;
import com.citibridge.sanctionScreening.repo.TransactionRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    @Autowired
    private TransactionRepo repo;
    @Override
    public boolean hasCsvFormat(MultipartFile file) {
        String type="text/csv";
        long size=400;
        //other validations here
        if(!type.equals(file.getContentType()))
         return false;
        if(!(file.getSize()<size))
            return false;
        return true;
    }

    @Override
    public void processAndSaveData(MultipartFile file)  {
        try {
            List<Transaction> transactions=csvToTransactions(file.getInputStream());
            repo.saveAll(transactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<Transaction> csvToTransactions(InputStream inputStream){

        try (BufferedReader fileReader= new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){
            List<Transaction> transactions=new ArrayList<Transaction>();
            List<CSVRecord> records=csvParser.getRecords();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            for(CSVRecord csvRecord : records){
                Transaction transaction=new Transaction(csvRecord.get("id"), LocalDate.parse(csvRecord.get("date"),formatter),csvRecord.get("Payer Name"),csvRecord.get("Payer Account"),csvRecord.get("Payee Name"), csvRecord.get("Payee Account"),Double.parseDouble(csvRecord.get("amount")) );
                transactions.add(transaction);

            }
            return transactions;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
