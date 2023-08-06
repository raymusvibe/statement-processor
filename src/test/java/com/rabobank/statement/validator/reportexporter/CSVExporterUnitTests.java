package com.rabobank.statement.validator.reportexporter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rabobank.statement.validator.dto.InvalidRecord;
import com.rabobank.statement.validator.exception.ReportExporterException;
import com.rabobank.statement.validator.reportexporter.abstractions.ReportExporter;
import java.io.Writer;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CSVExporterUnitTests {
    @Autowired
    ReportExporter reportExporter;

    private final Writer writer = Writer.nullWriter();

    @Test
    void CSVExporter_MissingFieldData_ReportWriteException() {
        InvalidRecord emptyRecord = new InvalidRecord();
        List<InvalidRecord> invalidRecords = List.of(emptyRecord);

        assertThrows(ReportExporterException.class, () -> reportExporter.writeFailedRecords(invalidRecords, writer));
    }

    @Test
    void CSVExporter_ValidFieldData_NoReportWriteException() {
        InvalidRecord record = new InvalidRecord(1L, "Some transaction");
        List<InvalidRecord> invalidRecords = List.of(record);

        assertDoesNotThrow(() -> reportExporter.writeFailedRecords(invalidRecords, writer));
    }
}
