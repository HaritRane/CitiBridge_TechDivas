package com.citibridge.sanctionScreening.controller;

import com.citibridge.sanctionScreening.entity.Keywords;
import com.citibridge.sanctionScreening.repo.KeywordRepo;
import com.citibridge.sanctionScreening.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/keywords")
public class KeywordController {
    @Autowired
    KeywordRepo keywordRepo;

    @PostMapping
    public ResponseEntity<ResponseMessage> addKeywords(@RequestBody List<String> names){
        for(String name:names){
        Keywords keywords=new Keywords(name);
        keywordRepo.save(keywords);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Added keywords"));
    }
}
