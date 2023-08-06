package com.rabobank.statement.validator.reportexporter.abstractions;

import com.rabobank.statement.validator.dto.InvalidRecord;
import java.io.Writer;
import java.util.List;

public interface ReportExporter {
    void writeFailedRecords(List<InvalidRecord> failedRecords, Writer writer);
}
