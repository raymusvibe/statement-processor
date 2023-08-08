package com.rabobank.statement.processor.statementreader;

import com.rabobank.statement.processor.enums.FileType;
import com.rabobank.statement.processor.exception.UnsupportedFileTypeException;
import com.rabobank.statement.processor.statementreader.abstractions.StatementReader;

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
