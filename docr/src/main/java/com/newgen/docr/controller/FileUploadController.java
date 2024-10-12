package com.newgen.docr.controller;

import com.newgen.docr.dao.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
public class FileUploadController {
    @Autowired
    FileUploadService fileUploadService;
    @GetMapping("/upload")
     String uploadPage() {
        return "upload"; // Returns the "upload.html" Thymeleaf template
    }

    @PostMapping("/api/files/upload")
    @ResponseBody
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("tableName") String tableName) {

        try {
            String fileExtension = fileUploadService.getFileExtension(file.getOriginalFilename());
            List<Map<String, String>> records;

            // Read file based on its type
            if (fileExtension.equals("csv")) {
                records = fileUploadService.readCsvFile(file);
            } else if (fileExtension.equals("xlsx") || fileExtension.equals("xlsm")||fileExtension.equalsIgnoreCase("xls")) {
                records = fileUploadService.readExcelFile(file);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Unsupported file format");
            }

            // Generate SQL INSERT queries
            String insertQuery = fileUploadService.generateInsertQuery(records, tableName);

            return ResponseEntity.ok(insertQuery);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }
}
