package com.rabobank.statement.processor.filereader;

import com.rabobank.statement.processor.enums.FileType;
import com.rabobank.statement.processor.exception.UnsupportedFileTypeException;
import com.rabobank.statement.processor.filereader.abstractions.FileReader;

public class FileReaderFactory {
    private FileReaderFactory() {
        throw new UnsupportedOperationException("Factory class should not be instantiated");
    }

    public static FileReader getFileReader(FileType fileType) {
        switch (fileType) {
            case CSV:
                return new CsvFileReader();
            case XML:
                return new XmlFileReader();
            default:
                throw new UnsupportedFileTypeException("The provided file type is not supported");
        }
    }
}
