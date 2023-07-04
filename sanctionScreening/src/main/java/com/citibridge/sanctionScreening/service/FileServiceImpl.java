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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    @Value("${upload.directory}")
    private String uploadDirectory;
    @Autowired
    private TransactionRepo repo;

    @Autowired
    private FileRepo fileRepo;

    private int noOfRecords;
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
    public String processAndSaveData(MultipartFile file)  {


        try {
            List<Transaction> transactions=csvToTransactions(file);
            if(transactions==null){
                return "Not valid file.Column is missing";
            }
            else {
                try {
                    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                    File directory =new File(uploadDirectory);
                    if (!directory.exists()) {
                        directory.mkdirs();
                        File destFile = new File(uploadDirectory + fileName);
                        file.transferTo(destFile);
                        repo.saveAll(transactions);
                        return "File Upload Done.";
                        //return "File archived.";
                    }
                }catch (IOException e){
                    return "Failed to archive file";

                }

                    return null;


            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @Override
    public int getNumberOfRecords() {
        return noOfRecords;
    }

    private List<Transaction> csvToTransactions(MultipartFile file){

        try (BufferedReader fileReader= new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){
            List<Transaction> transactions=new ArrayList<Transaction>();
            List<CSVRecord> records=csvParser.getRecords();
            this.noOfRecords=records.size();
           int headers =csvParser.getHeaderMap().size();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
           if(headers==9){
               File_entity file1=new File_entity(file.getOriginalFilename());
               fileRepo.save(file1);
               for(CSVRecord csvRecord : records){
                   Transaction transaction=new Transaction(csvRecord.get("id"),file1.getFileId(), LocalDate.parse(csvRecord.get("date"),formatter),csvRecord.get("Payer Name"),csvRecord.get("Payer Account"),csvRecord.get("Payee Name"), csvRecord.get("Payee Account"),Double.parseDouble(csvRecord.get("amount")) );
                   transactions.add(transaction);

               }
               return transactions;
           }else return null;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
