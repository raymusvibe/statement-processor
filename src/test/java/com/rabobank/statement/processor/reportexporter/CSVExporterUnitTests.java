package com.rabobank.statement.processor.reportexporter;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rabobank.statement.processor.dto.InvalidRecord;
import com.rabobank.statement.processor.exception.ReportWriteException;
import com.rabobank.statement.processor.reportexporter.abstractions.ReportExporter;
import java.io.Writer;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CSVExporterUnitTests {
    @Autowired
    ReportExporter reportExporter;

    @Test
    void CSVExporter_MissingFieldData_ReportWriteException() {
        InvalidRecord emptyRecord = new InvalidRecord();
        List<InvalidRecord> invalidRecords = List.of(emptyRecord);
        Writer writer = Writer.nullWriter();

        assertThrows(ReportWriteException.class, () -> reportExporter.writeFailedRecords(invalidRecords, writer));
    }
}
