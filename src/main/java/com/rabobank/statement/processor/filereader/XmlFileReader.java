package com.rabobank.statement.processor.filereader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rabobank.statement.processor.exception.StatementValidationException;
import com.rabobank.statement.processor.filereader.abstractions.FileReader;
import com.rabobank.statement.processor.model.StatementRecord;
import com.rabobank.statement.processor.model.xml.XmlRecordsRoot;
import com.rabobank.statement.processor.model.xml.XmlStatementRecord;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class XmlFileReader implements FileReader {
    private final ModelMapper modelMapper;

    public XmlFileReader() {
        modelMapper = new ModelMapper();
    }

    @Override
    public List<StatementRecord> readRecords(InputStream inputStream) {
        XmlMapper xmlMapper = new XmlMapper();
        XmlRecordsRoot recordsRoot;
        try {
            recordsRoot = xmlMapper.readValue(inputStream, XmlRecordsRoot.class);
        } catch (IOException e) {
            throw new StatementValidationException(e);
        }
        return recordsRoot.recordsList.parallelStream()
                .map(this::mapToStatementRecord)
                .toList();
    }

    private StatementRecord mapToStatementRecord(XmlStatementRecord record) {
        return modelMapper.map(record, StatementRecord.class);
    }
}
