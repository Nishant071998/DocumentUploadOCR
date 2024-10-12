# DocumentUploadOCR

Spring Boot File Upload Service with Thymeleaf
This project is a Spring Boot application that provides a REST service for uploading various file types (CSV, XLSX, XLSM, XLS) and dynamically generating SQL INSERT statements based on the contents of the uploaded files. The first row of the file is treated as the database table's column names, and subsequent rows are considered as the data values.

The project also uses Thymeleaf to create a simple web interface where users can upload files and specify the target database table name. The files are processed on the backend, and SQL queries are generated for potential insertion into a database.

Features:
Supports file formats: .csv, .xlsx, .xlsm, and .xls
Reads the first row of the file as headers (database column names)
Generates SQL INSERT statements dynamically based on the uploaded file's content
Web interface built with Thymeleaf for file upload and table name input
Backend implementation in Spring Boot with REST endpoints
Handles file uploads using MultipartFile in Spring Boot
Technologies:
Java 8 (or higher)
Spring Boot
Thymeleaf (for web interface)
Apache POI (for reading Excel files)
OpenCSV (for reading CSV files)
Maven (for build and dependency management)
