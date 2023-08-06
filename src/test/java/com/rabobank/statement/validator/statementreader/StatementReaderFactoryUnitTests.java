package com.rabobank.statement.validator.statementreader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rabobank.statement.validator.enums.FileType;
import com.rabobank.statement.validator.exception.UnsupportedFileTypeException;
import com.rabobank.statement.validator.statementreader.abstractions.StatementReader;
import org.junit.jupiter.api.Test;

class StatementReaderFactoryUnitTests {

    @Test
    void StatementReaderFactory_WhenXmlFileType_XmlFileReader() {
        StatementReader statementReader = StatementReaderFactory.getStatementReader(FileType.XML);

        assertEquals(statementReader.getClass(), XmlStatementReader.class);
    }

    @Test
    void StatementReaderFactory_WhenCsvFileType_CsvFileReader() {
        StatementReader statementReader = StatementReaderFactory.getStatementReader(FileType.CSV);

        assertEquals(statementReader.getClass(), CsvStatementReader.class);
    }

    @Test
    void StatementReaderFactory_WhenUnsupportedFileType_UnsupportedFileTypeException() {
        assertThrows(
                UnsupportedFileTypeException.class, () -> StatementReaderFactory.getStatementReader(FileType.OTHER));
    }
}
