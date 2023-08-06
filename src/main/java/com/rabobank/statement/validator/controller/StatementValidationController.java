package com.rabobank.statement.validator.controller;

import com.rabobank.statement.validator.dto.InvalidRecord;
import com.rabobank.statement.validator.dto.ValidationResult;
import com.rabobank.statement.validator.reportexporter.abstractions.ReportExporter;
import com.rabobank.statement.validator.service.abstractions.StatementValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1/report")
@Tag(name = "Statement Validation Controller", description = "API to validate customer statement records")
@Validated
public class StatementValidationController {

    @Autowired
    StatementValidationService validationService;

    @Autowired
    ReportExporter reportExporter;

    private final Logger logger = LoggerFactory.getLogger(StatementValidationController.class);

    @Operation(
            summary = "Validate CSV and XML statement records and return failed records in JSON format",
            responses = {
                @ApiResponse(responseCode = "200", description = "Statement file was successfully validated"),
                @ApiResponse(responseCode = "400", description = "Bad request")
            })
    @PostMapping(consumes = "multipart/form-data")
    public ValidationResult validateFile(@NotNull @RequestParam("file") MultipartFile inputFile) {
        logger.info("Validating input file");
        return new ValidationResult(validationService.validateStatementFile(inputFile));
    }

    @Operation(
            summary = "Validate CSV and XML statement records and export failed records to CSV",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Statement file was successfully validated and exported"),
                @ApiResponse(responseCode = "400", description = "Bad request")
            })
    @PostMapping(consumes = "multipart/form-data", path = "/csv", produces = "text/csv")
    public void exportCSV(@NotNull @RequestParam("file") MultipartFile inputFile, HttpServletResponse response)
            throws IOException {
        logger.info("Validating input file for export");
        List<InvalidRecord> failedRecords = validationService.validateStatementFile(inputFile);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=validated-statement-records.csv");
        reportExporter.writeFailedRecords(failedRecords, response.getWriter());
    }
}
