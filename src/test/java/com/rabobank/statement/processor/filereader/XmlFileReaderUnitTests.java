package com.rabobank.statement.processor.filereader;

import static org.junit.jupiter.api.Assertions.*;

import com.rabobank.statement.processor.exception.StatementValidationException;
import com.rabobank.statement.processor.model.StatementRecord;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

class XmlFileReaderUnitTests {
    XmlFileReader xmlFileReader = new XmlFileReader();
    private final Resource empty_records = new ClassPathResource("import/xml/empty_records.xml");
    private final Resource valid_records = new ClassPathResource("import/xml/valid_records.xml");
    private final Resource malformed_records = new ClassPathResource("import/csv/malformed_records.csv");

    @Test
    void XmlFileReader_WhenEmptyUpload_NoRecords() throws IOException {
        List<StatementRecord> statementRecordList = xmlFileReader.readRecords(empty_records.getInputStream());

        assertTrue(statementRecordList.isEmpty());
    }

    @Test
    void XmlFileReader_WhenValidUpload_CorrectRecordSize() throws IOException {
        int recordSize = 4;

        List<StatementRecord> statementRecordList = xmlFileReader.readRecords(valid_records.getInputStream());

        assertEquals(recordSize, statementRecordList.size());
    }

    @Test
    void XmlFileReader_WhenMalformedUpload_StatementValidationException() throws IOException {
        InputStream inputStream = malformed_records.getInputStream();

        assertThrows(StatementValidationException.class, () -> xmlFileReader.readRecords(inputStream));
    }
}
