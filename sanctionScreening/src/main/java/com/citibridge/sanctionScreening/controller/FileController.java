package com.citibridge.sanctionScreening.controller;

import com.citibridge.sanctionScreening.entity.Transaction;
import com.citibridge.sanctionScreening.response.ResponseMessage;
import com.citibridge.sanctionScreening.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
        public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    if(fileService.hasCsvFormat(file)){
    String up= fileService.processAndSaveData(file);
    if(up=="File Upload Done.")
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Uploaded file successfully: "+file.getOriginalFilename()));
    else
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(up));

    }
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Please upload csv file file must be of size less than 2KB."));
        }
    }

