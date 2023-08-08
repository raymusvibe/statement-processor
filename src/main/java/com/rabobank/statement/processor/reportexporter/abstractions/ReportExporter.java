package com.rabobank.statement.processor.reportexporter.abstractions;

import com.rabobank.statement.processor.dto.InvalidRecord;
import java.io.Writer;
import java.util.List;

public interface ReportExporter {
    void writeFailedRecords(List<InvalidRecord> failedRecords, Writer writer);
}
