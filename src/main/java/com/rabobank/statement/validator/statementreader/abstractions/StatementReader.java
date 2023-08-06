package com.rabobank.statement.validator.statementreader.abstractions;

import com.rabobank.statement.validator.model.StatementRecord;
import java.io.InputStream;
import java.util.List;

public interface StatementReader {
    List<StatementRecord> readRecords(InputStream inputStream);
}
