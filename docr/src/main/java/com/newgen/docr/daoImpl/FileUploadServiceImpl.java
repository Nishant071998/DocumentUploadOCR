package com.newgen.docr.daoImpl;

import com.newgen.docr.dao.FileUploadService;
import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Override
     public  String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
    @Override
    public List<Map<String, String>> readCsvFile(MultipartFile file) throws Exception {
        List<Map<String, String>> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVReader csvReader = new CSVReader(reader);
            String[] headers = csvReader.readNext(); // First row is header

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                Map<String, String> record = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    record.put(headers[i], line[i]);
                }
                records.add(record);
            }
        }
        return records;
    }
    @Override
    public List<Map<String, String>> readExcelFile(MultipartFile file) throws Exception {
        List<Map<String, String>> records = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            Row headerRow = rowIterator.next(); // First row is header
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Map<String, String> record = new HashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.getCell(i);
                    record.put(headers.get(i), cell.toString());
                }
                records.add(record);
            }
        }
        return records;
    }
    @Override
    public String generateInsertQuery(List<Map<String, String>> records, String tableName) {
        if (records.isEmpty()) return "";

        StringBuilder queryBuilder = new StringBuilder();
        String[] columns = records.get(0).keySet().toArray(new String[0]);

        for (Map<String, String> record : records) {
            String columnList = String.join(", ", columns);
            String valueList = Arrays.stream(columns)
                    .map(column -> "'" + record.get(column).replace("'", "''") + "'")
                    .collect(Collectors.joining(", "));

            queryBuilder.append(String.format("INSERT INTO %s (%s) VALUES (%s);%n",
                    tableName, columnList, valueList));
        }
        return queryBuilder.toString();
    }
}
