package com.citibridge.sanctionScreening.controller;

import com.citibridge.sanctionScreening.entity.File_entity;
import com.citibridge.sanctionScreening.entity.Transaction;
import com.citibridge.sanctionScreening.repo.FileRepo;
import com.citibridge.sanctionScreening.response.ResponseMessage;
import com.citibridge.sanctionScreening.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private FileRepo fileRepo;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (fileService.hasCsvFormat(file)) {
            String up = fileService.processAndSaveData(file);
            if (up == "File Upload Done.")
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Uploaded file successfully: " + file.getOriginalFilename()));
            else
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(up));

        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Please upload csv file file must be of size less than 2KB."));
    }

    @GetMapping("/all")
    public List<String> getAllFileNames(){
        List<String> filenames=new ArrayList<>();
        List<File_entity> files = fileRepo.findAll();
        for(File_entity file : files){
            filenames.add(file.getFileName());
        }
        return filenames;
    }

    @GetMapping("/allFiles")
    public List<File_entity> getAllFile(){
       return fileRepo.findAll();
    }
    @GetMapping("/countFiles")
    public long fileCount(){
        return fileRepo.count();
    }

}

