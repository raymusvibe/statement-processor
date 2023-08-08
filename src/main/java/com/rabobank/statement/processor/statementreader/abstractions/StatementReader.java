package com.rabobank.statement.processor.statementreader.abstractions;

import com.rabobank.statement.processor.model.StatementRecord;
import java.io.InputStream;
import java.util.List;

public interface StatementReader {
    List<StatementRecord> readRecords(InputStream inputStream);
}
