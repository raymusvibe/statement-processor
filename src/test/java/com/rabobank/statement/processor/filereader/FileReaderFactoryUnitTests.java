package com.rabobank.statement.processor.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rabobank.statement.processor.enums.FileType;
import com.rabobank.statement.processor.exception.UnsupportedFileTypeException;
import com.rabobank.statement.processor.filereader.abstractions.FileReader;
import org.junit.jupiter.api.Test;

public class FileReaderFactoryUnitTests {

    @Test
    void FileReaderFactory_WhenXmlFileType_XmlFileReader() {
        FileReader fileReader = FileReaderFactory.getFileReader(FileType.XML);

        assertEquals(fileReader.getClass(), XmlFileReader.class);
    }

    @Test
    void FileReaderFactory_WhenCsvFileType_CsvFileReader() {
        FileReader fileReader = FileReaderFactory.getFileReader(FileType.CSV);

        assertEquals(fileReader.getClass(), CsvFileReader.class);
    }

    @Test
    void FileReaderFactory_WhenUnsupportedFileType_UnsupportedFileTypeException() {
        assertThrows(UnsupportedFileTypeException.class, () -> FileReaderFactory.getFileReader(FileType.OTHER));
    }
}
