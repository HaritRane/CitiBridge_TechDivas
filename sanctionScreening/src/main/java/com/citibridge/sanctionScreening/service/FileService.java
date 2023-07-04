package com.citibridge.sanctionScreening.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    boolean hasCsvFormat(MultipartFile file);

    String processAndSaveData(MultipartFile file) throws IOException;
    int getNumberOfRecords();
}
