package com.rabobank.statement.processor.service;

import com.rabobank.statement.processor.dto.InvalidRecord;
import com.rabobank.statement.processor.dto.ValidationResult;
import com.rabobank.statement.processor.enums.FileType;
import com.rabobank.statement.processor.exception.StatementValidationException;
import com.rabobank.statement.processor.filereader.FileReaderFactory;
import com.rabobank.statement.processor.service.abstractions.StatementValidationService;
import com.rabobank.statement.processor.validator.StatementValidator;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StatementValidationServiceImpl implements StatementValidationService {
    public ValidationResult validateStatementFile(MultipartFile file) {
        List<InvalidRecord> failedRecords = Optional.of(file)
                .map(MultipartFile::getContentType)
                .map(FileType::fromMimeType)
                .map(FileReaderFactory::getFileReader)
                .map(fileReader -> fileReader.readRecords(getInputStream(file)))
                .map(StatementValidator::validate)
                .orElseThrow(() -> new StatementValidationException("Failed to read statement data"));

        return new ValidationResult(failedRecords);
    }

    private InputStream getInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new StatementValidationException(e);
        }
    }
}
