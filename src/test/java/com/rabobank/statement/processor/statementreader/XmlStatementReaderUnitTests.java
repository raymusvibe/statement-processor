package com.rabobank.statement.processor.statementreader;

import static org.junit.jupiter.api.Assertions.*;

import com.rabobank.statement.processor.exception.StatementValidationException;
import com.rabobank.statement.processor.model.StatementRecord;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

class XmlStatementReaderUnitTests {
    XmlStatementReader xmlReader = new XmlStatementReader();
    private final Resource empty_records = new ClassPathResource("import/xml/empty_records.xml");
    private final Resource valid_records = new ClassPathResource("import/xml/valid_records.xml");
    private final Resource malformed_records = new ClassPathResource("import/xml/malformed_records.xml");

    @Test
    void XmlStatementReader_WhenEmptyUpload_NoRecords() throws IOException {
        List<StatementRecord> statementRecordList = xmlReader.readRecords(empty_records.getInputStream());

        assertTrue(statementRecordList.isEmpty());
    }

    @Test
    void XmlStatementReader_WhenValidUpload_CorrectRecordSize() throws IOException {
        int recordSize = 4;

        List<StatementRecord> statementRecordList = xmlReader.readRecords(valid_records.getInputStream());

        assertEquals(recordSize, statementRecordList.size());
    }

    @Test
    void XmlStatementReader_WhenMalformedUpload_StatementValidationException() throws IOException {
        InputStream inputStream = malformed_records.getInputStream();

        assertThrows(StatementValidationException.class, () -> xmlReader.readRecords(inputStream));
    }
}
