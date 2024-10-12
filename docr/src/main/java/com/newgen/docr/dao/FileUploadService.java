package com.newgen.docr.dao;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface FileUploadService {


    String getFileExtension(String originalFilename);

    List<Map<String, String>> readCsvFile(MultipartFile file) throws Exception;

    List<Map<String, String>> readExcelFile(MultipartFile file) throws Exception;

    String generateInsertQuery(List<Map<String, String>> records, String tableName);
}
