package com.rabobank.statement.processor.service;

import static org.junit.jupiter.api.Assertions.*;

import com.rabobank.statement.processor.dto.ValidationResult;
import com.rabobank.statement.processor.exception.StatementValidationException;
import com.rabobank.statement.processor.exception.UnsupportedFileTypeException;
import com.rabobank.statement.processor.service.abstractions.StatementValidationService;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
class StatementValidationServiceIntegrationTests {
    @Autowired
    private StatementValidationService statementValidationService;

    private final Resource unsupported_format_records = new ClassPathResource("unsupported_format/valid_records.json");
    private final Resource malformed_records = new ClassPathResource("xml/malformed_records.xml");
    private final Resource empty_records = new ClassPathResource("csv/empty_records.csv");
    private final Resource invalid_records = new ClassPathResource("csv/invalid_records.csv");

    @Test
    void StatementValidationService_WhenUnsupportedFileFormat_UnsupportedFileTypeException() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "unsupported_format/valid_records.json",
                "text/json",
                unsupported_format_records.getInputStream());

        assertThrows(UnsupportedFileTypeException.class, () -> statementValidationService.validateStatementFile(file));
    }

    @Test
    void StatementValidationService_WhenMalformedInputFile_StatementValidationException() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file", "xml/malformed_records.xml", "text/xml", malformed_records.getInputStream());

        assertThrows(StatementValidationException.class, () -> statementValidationService.validateStatementFile(file));
    }

    @Test
    void StatementValidationService_WhenEmptyInputFile_NoValidationFailure() throws IOException {
        MockMultipartFile file =
                new MockMultipartFile("file", "csv/empty_records.csv", "text/csv", empty_records.getInputStream());

        ValidationResult validationResult = statementValidationService.validateStatementFile(file);

        assertTrue(validationResult.invalidRecords().isEmpty());
    }

    @Test
    void StatementValidationService_WhenInvalidRecords_CorrectFailureSize() throws IOException {
        int numberOfInvalidRecords = 3;
        MockMultipartFile file =
                new MockMultipartFile("file", "csv/invalid_records.csv", "text/csv", invalid_records.getInputStream());

        ValidationResult validationResult = statementValidationService.validateStatementFile(file);

        assertEquals(numberOfInvalidRecords, validationResult.invalidRecords().size());
    }
}
