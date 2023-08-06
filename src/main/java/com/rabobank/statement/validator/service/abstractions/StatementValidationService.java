package com.rabobank.statement.validator.service.abstractions;

import com.rabobank.statement.validator.dto.InvalidRecord;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface StatementValidationService {
    public List<InvalidRecord> validateStatementFile(MultipartFile inputFile);
}
