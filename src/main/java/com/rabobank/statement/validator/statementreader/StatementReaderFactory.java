package com.rabobank.statement.validator.statementreader;

import com.rabobank.statement.validator.enums.FileType;
import com.rabobank.statement.validator.exception.UnsupportedFileTypeException;
import com.rabobank.statement.validator.statementreader.abstractions.StatementReader;

public class StatementReaderFactory {
    private StatementReaderFactory() {
        throw new UnsupportedOperationException("Factory class should not be instantiated");
    }

    public static StatementReader getStatementReader(FileType fileType) {
        return switch (fileType) {
            case CSV -> new CsvStatementReader();
            case XML -> new XmlStatementReader();
            default -> throw new UnsupportedFileTypeException("The provided file type is not supported");
        };
    }
}
