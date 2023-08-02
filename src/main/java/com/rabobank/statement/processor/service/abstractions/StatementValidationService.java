package com.rabobank.statement.processor.service.abstractions;

import com.rabobank.statement.processor.dto.ValidationResult;
import org.springframework.web.multipart.MultipartFile;

public interface StatementValidationService {
    public ValidationResult validateStatementFile(MultipartFile inputFile);
}
