# DocumentUploadOCR
This project is a Spring Boot application that provides a REST service for uploading various file types (CSV, XLSX, XLSM, XLS) and dynamically generating SQL INSERT statements based on the contents of the uploaded files. The first row of the file is treated as the database table's column names, and subsequent rows are considered as the data values.
