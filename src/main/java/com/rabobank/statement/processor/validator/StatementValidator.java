package com.rabobank.statement.processor.validator;

import com.rabobank.statement.processor.dto.InvalidRecord;
import com.rabobank.statement.processor.model.StatementRecord;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StatementValidator {
    public static List<InvalidRecord> validate(List<StatementRecord> records) {
        return records.parallelStream()
                .filter(record -> hasWrongEndBalance(record) || hasDuplicateReference(records, record))
                .map(StatementValidator::createFailedRecord)
                .collect(Collectors.toList());
    }

    private static boolean hasWrongEndBalance(StatementRecord record) {
        return !record.getStartBalance().add(record.getMutation()).equals(record.getEndBalance());
    }

    private static boolean hasDuplicateReference(List<StatementRecord> records, StatementRecord record) {
        return Collections.frequency(records, record) > 1;
    }

    private static InvalidRecord createFailedRecord(StatementRecord record) {
        return new InvalidRecord(record.getReference(), record.getDescription());
    }
}
