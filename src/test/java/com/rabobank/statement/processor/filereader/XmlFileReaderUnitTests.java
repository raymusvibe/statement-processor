package com.rabobank.statement.processor.filereader;

import static org.junit.jupiter.api.Assertions.*;

import com.rabobank.statement.processor.model.StatementRecord;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

class XmlFileReaderUnitTests {
    XmlFileReader xmlFileReader = new XmlFileReader();
    private final Resource empty_records = new ClassPathResource("xml/empty_records.xml");
    private final Resource valid_records = new ClassPathResource("xml/valid_records.xml");

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
}
