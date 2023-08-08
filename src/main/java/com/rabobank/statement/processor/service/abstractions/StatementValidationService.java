package com.rabobank.statement.processor.service.abstractions;

import com.rabobank.statement.processor.dto.InvalidRecord;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface StatementValidationService {
    public List<InvalidRecord> validateStatementFile(MultipartFile inputFile);
}
