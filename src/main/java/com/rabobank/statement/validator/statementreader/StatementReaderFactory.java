package com.rabobank.statement.validator.statementreader;

import com.rabobank.statement.validator.enums.FileType;
import com.rabobank.statement.validator.exception.UnsupportedFileTypeException;
import com.rabobank.statement.validator.statementreader.abstractions.StatementReader;

public class StatementReaderFactory {
    private StatementReaderFactory() {
        throw new UnsupportedOperationException("Factory class should not be instantiated");
    }

    public static StatementReader getFileReader(FileType fileType) {
        switch (fileType) {
            case CSV:
                return new CsvStatementReader();
            case XML:
                return new XmlStatementReader();
            default:
                throw new UnsupportedFileTypeException("The provided file type is not supported");
        }
    }
}
