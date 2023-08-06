package com.rabobank.statement.validator.statementreader;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rabobank.statement.validator.exception.StatementValidationException;
import com.rabobank.statement.validator.model.StatementRecord;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

class CsvStatementReaderUnitTests {
    CsvStatementReader csvReader = new CsvStatementReader();
    private final Resource empty_records = new ClassPathResource("import/csv/empty_records.csv");
    private final Resource valid_records = new ClassPathResource("import/csv/valid_records.csv");
    private final Resource malformed_records = new ClassPathResource("import/csv/malformed_records.csv");

    @Test
    void CsvStatementReader_WhenEmptyUpload_NoRecords() throws IOException {
        List<StatementRecord> statementRecordList = csvReader.readRecords(empty_records.getInputStream());

        assertTrue(statementRecordList.isEmpty());
    }

    @Test
    void CsvStatementReader_WhenValidUpload_CorrectRecordSize() throws IOException {
        int recordSize = 10;

        List<StatementRecord> statementRecordList = csvReader.readRecords(valid_records.getInputStream());

        assertEquals(recordSize, statementRecordList.size());
    }

    @Test
    void CsvStatementReader_WhenMalformedUpload_StatementValidationException() throws IOException {
        InputStream inputStream = malformed_records.getInputStream();

        assertThrows(StatementValidationException.class, () -> csvReader.readRecords(inputStream));
    }
}
