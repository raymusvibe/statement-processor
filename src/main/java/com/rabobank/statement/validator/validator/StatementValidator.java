package com.rabobank.statement.validator.validator;

import com.rabobank.statement.validator.dto.InvalidRecord;
import com.rabobank.statement.validator.model.StatementRecord;
import java.util.Collections;
import java.util.List;

public class StatementValidator {
    private StatementValidator() {
        throw new UnsupportedOperationException("Class should not be instantiated");
    }

    public static List<InvalidRecord> validate(List<StatementRecord> records) {
        return records.parallelStream()
                .filter(statementRecord ->
                        hasWrongEndBalance(statementRecord) || hasDuplicateReference(records, statementRecord))
                .map(StatementValidator::createFailedRecord)
                .toList();
    }

    private static boolean hasWrongEndBalance(StatementRecord statementRecord) {
        return !statementRecord
                .getStartBalance()
                .add(statementRecord.getMutation())
                .equals(statementRecord.getEndBalance());
    }

    private static boolean hasDuplicateReference(List<StatementRecord> records, StatementRecord statementRecord) {
        return Collections.frequency(records, statementRecord) > 1;
    }

    private static InvalidRecord createFailedRecord(StatementRecord statementRecord) {
        return new InvalidRecord(statementRecord.getReference(), statementRecord.getDescription());
    }
}
