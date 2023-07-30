package com.citibridge.sanctionScreening.service;

import com.citibridge.sanctionScreening.entity.File_entity;
import com.citibridge.sanctionScreening.entity.Transaction;
import com.citibridge.sanctionScreening.repo.FileRepo;
import com.citibridge.sanctionScreening.repo.TransactionRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {


    @Autowired
    private TransactionRepo repo;
    @Autowired
    private FileRepo fileRepo;
    private int noOfRecords;

    public boolean isUpload=false;

    @Override
    public boolean hasCsvFormat(MultipartFile file) {
        String type = "text/csv";
        long size = 2000;
        //other validations here
        if (!type.equals(file.getContentType()))
            return false;
        return file.getSize() < size;
    }

    @Override
    public String processAndSaveData(MultipartFile file) throws IOException {


        try {
            List<Transaction> transactions = csvToTransactions(file);
            if (transactions == null) {
                return "Not valid file.Column is missing";
            } else {


                repo.saveAll(transactions);
return "File upload done";


            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return null;
    }

    @Override
    public int getNumberOfRecords() {
        return noOfRecords;
    }

    private List<Transaction> csvToTransactions(MultipartFile file) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<Transaction> transactions = new ArrayList<Transaction>();
            List<CSVRecord> records = csvParser.getRecords();
            this.noOfRecords = records.size();
            int headers = csvParser.getHeaderMap().size();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            if (headers == 7) {
                String filePath = "C:\\Users\\raneh\\Downloads\\demo" + "\\" + file.getOriginalFilename();
                File_entity file_entity = fileRepo.save(File_entity.builder()
                        .fileName(file.getOriginalFilename())
                        .filePath(filePath).build());
                file.transferTo(new File(filePath));
                isUpload=true;
                //File_entity file1 = new File_entity(file.getOriginalFilename());
                //fileRepo.save(file1);
                for (CSVRecord csvRecord : records) {
                    Transaction transaction = new Transaction( csvRecord.get(0), file_entity.getFileId(), LocalDate.parse(csvRecord.get("date"), formatter), csvRecord.get("Payer Name"), csvRecord.get("Payer Account"), csvRecord.get("Payee Name"), csvRecord.get("Payee Account"), Double.parseDouble(csvRecord.get("amount")));
                    transactions.add(transaction);

                }
                return transactions;
            } else return null;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
