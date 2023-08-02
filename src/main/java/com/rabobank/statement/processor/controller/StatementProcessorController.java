package com.rabobank.statement.processor.controller;

import com.rabobank.statement.processor.dto.ValidationResult;
import com.rabobank.statement.processor.service.abstractions.StatementValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1/validate")
@Tag(name = "Statement Processor Controller", description = "API to validate customer statement records")
@Validated
public class StatementProcessorController {

    @Autowired
    StatementValidationService validationService;

    private final Logger logger = LoggerFactory.getLogger(StatementProcessorController.class);

    @Operation(
            summary = "Validate statements through CSV and XML files",
            responses = {
                @ApiResponse(responseCode = "200", description = "Statement file was successfully validated"),
                @ApiResponse(responseCode = "400", description = "Bad request")
            })
    @PostMapping(consumes = "multipart/form-data")
    public ValidationResult validateFile(@NotNull @RequestParam("file") MultipartFile inputFile) {
        logger.info("Validating input file");
        return validationService.validateStatementFile(inputFile);
    }
}
