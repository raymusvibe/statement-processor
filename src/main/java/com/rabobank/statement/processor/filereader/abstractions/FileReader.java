package com.rabobank.statement.processor.filereader.abstractions;

import com.rabobank.statement.processor.model.StatementRecord;
import java.io.InputStream;
import java.util.List;

public interface FileReader {
    List<StatementRecord> readRecords(InputStream inputStream);
}
