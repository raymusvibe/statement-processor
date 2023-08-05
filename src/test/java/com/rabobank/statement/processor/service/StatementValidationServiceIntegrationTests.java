package com.rabobank.statement.processor.service;

import static org.junit.jupiter.api.Assertions.*;

import com.rabobank.statement.processor.dto.InvalidRecord;
import com.rabobank.statement.processor.exception.StatementValidationException;
import com.rabobank.statement.processor.exception.UnsupportedFileTypeException;
import com.rabobank.statement.processor.service.abstractions.StatementValidationService;
import java.io.IOException;
import java.util.List;
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

    private final Resource unsupported_format_records =
            new ClassPathResource("import/unsupported_format/valid_records.json");
    private final Resource malformed_records = new ClassPathResource("import/xml/malformed_records.xml");
    private final Resource empty_records = new ClassPathResource("import/csv/empty_records.csv");
    private final Resource invalid_records = new ClassPathResource("import/csv/invalid_records.csv");

    @Test
    void StatementValidationService_WhenUnsupportedFileFormat_UnsupportedFileTypeException() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "import/xml/unsupported_format/valid_records.json",
                "text/json",
                unsupported_format_records.getInputStream());

        assertThrows(UnsupportedFileTypeException.class, () -> statementValidationService.validateStatementFile(file));
    }

    @Test
    void StatementValidationService_WhenMalformedInputFile_StatementValidationException() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file", "import/xml/malformed_records.xml", "text/xml", malformed_records.getInputStream());

        assertThrows(StatementValidationException.class, () -> statementValidationService.validateStatementFile(file));
    }

    @Test
    void StatementValidationService_WhenEmptyInputFile_NoValidationFailure() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file", "import/xml/csv/empty_records.csv", "text/csv", empty_records.getInputStream());

        List<InvalidRecord> failedRecords = statementValidationService.validateStatementFile(file);

        assertTrue(failedRecords.isEmpty());
    }

    @Test
    void StatementValidationService_WhenInvalidRecords_CorrectFailureSize() throws IOException {
        int numberOfInvalidRecords = 3;
        MockMultipartFile file = new MockMultipartFile(
                "file", "import/xml/csv/invalid_records.csv", "text/csv", invalid_records.getInputStream());

        List<InvalidRecord> failedRecords = statementValidationService.validateStatementFile(file);

        assertEquals(numberOfInvalidRecords, failedRecords.size());
    }
}
