package com.rabobank.statement.validator.reportexporter;

import static org.junit.jupiter.api.Assertions.*;

import com.rabobank.statement.validator.dto.InvalidRecord;
import com.rabobank.statement.validator.exception.ReportExporterException;
import com.rabobank.statement.validator.reportexporter.abstractions.ReportExporter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CSVExporterUnitTests {
    @Autowired
    ReportExporter reportExporter;

    private final Writer nullWriter = Writer.nullWriter();
    private final Writer stringWriter = new StringWriter();
    private final Resource expected_export = new ClassPathResource("export/expected_export.csv");

    @AfterAll
    void afterAll() throws IOException {
        nullWriter.close();
        stringWriter.close();
    }

    @Test
    void CSVExporter_MissingFieldData_ReportWriteException() {
        InvalidRecord emptyRecord = new InvalidRecord();
        List<InvalidRecord> invalidRecords = List.of(emptyRecord);

        assertThrows(
                ReportExporterException.class, () -> reportExporter.writeFailedRecords(invalidRecords, nullWriter));
    }

    @Test
    void CSVExporter_ValidFieldData_NoReportWriteException() {
        InvalidRecord record = new InvalidRecord(1L, "Some transaction");
        List<InvalidRecord> invalidRecords = List.of(record);

        assertDoesNotThrow(() -> reportExporter.writeFailedRecords(invalidRecords, nullWriter));
    }

    @Test
    void CSVExporter_ValidFieldData_CorrectReportContents() throws IOException {
        InvalidRecord record = new InvalidRecord(2000L, "Another transaction");
        List<InvalidRecord> invalidRecords = List.of(record);
        String expectedContent = expected_export.getContentAsString(StandardCharsets.UTF_8);

        reportExporter.writeFailedRecords(invalidRecords, stringWriter);

        assertEquals(expectedContent, stringWriter.toString());
    }
}
