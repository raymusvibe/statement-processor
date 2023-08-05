package com.rabobank.statement.processor.filereader;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rabobank.statement.processor.exception.StatementValidationException;
import com.rabobank.statement.processor.model.StatementRecord;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

class CsvFileReaderUnitTests {
    CsvFileReader csvFileReader = new CsvFileReader();
    private final Resource empty_records = new ClassPathResource("import/csv/empty_records.csv");
    private final Resource valid_records = new ClassPathResource("import/csv/valid_records.csv");
    private final Resource malformed_records = new ClassPathResource("import/csv/malformed_records.csv");

    @Test
    void CsvFileReader_WhenEmptyUpload_NoRecords() throws IOException {
        List<StatementRecord> statementRecordList = csvFileReader.readRecords(empty_records.getInputStream());

        assertTrue(statementRecordList.isEmpty());
    }

    @Test
    void CsvFileReader_WhenValidUpload_CorrectRecordSize() throws IOException {
        int recordSize = 10;

        List<StatementRecord> statementRecordList = csvFileReader.readRecords(valid_records.getInputStream());

        assertEquals(recordSize, statementRecordList.size());
    }

    @Test
    void CsvFileReader_WhenMalformedUpload_StatementValidationException() throws IOException {
        InputStream inputStream = malformed_records.getInputStream();

        assertThrows(StatementValidationException.class, () -> csvFileReader.readRecords(inputStream));
    }
}
