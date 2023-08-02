package com.rabobank.statement.processor.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rabobank.statement.processor.dto.InvalidRecord;
import com.rabobank.statement.processor.model.StatementRecord;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

public class StatementValidatorUnitTests {

    @Test
    void StatementValidator_WhenEmptyRecords_NoFailures() {
        List<StatementRecord> records = List.of();

        List<InvalidRecord> invalidRecords = StatementValidator.validate(records);

        assertTrue(invalidRecords.isEmpty());
    }

    @Test
    void StatementValidator_WhenValidRecord_NoFailures() {
        StatementRecord validRecord = StatementRecord.builder()
                .reference(98722L)
                .accountNumber("NLACCTEST")
                .description("A transaction")
                .startBalance(BigDecimal.ZERO)
                .mutation(BigDecimal.TEN)
                .endBalance(BigDecimal.TEN)
                .build();
        List<StatementRecord> records = List.of(validRecord);

        List<InvalidRecord> invalidRecords = StatementValidator.validate(records);

        assertTrue(invalidRecords.isEmpty());
    }

    @Test
    void StatementValidator_WhenInvalidBalance_CorrectFailure() {
        Long validBalanceRef = 77612L;
        Long invalidBalanceRef = 78680L;
        String validBalanceRecordDescription = "A transaction";
        String invalidBalanceRecordDescription = "Another transaction";
        StatementRecord validBalanceRecord = StatementRecord.builder()
                .reference(validBalanceRef)
                .accountNumber("NLTEST")
                .description(validBalanceRecordDescription)
                .startBalance(BigDecimal.ZERO)
                .mutation(BigDecimal.TEN)
                .endBalance(BigDecimal.TEN)
                .build();
        StatementRecord invalidBalanceRecord = StatementRecord.builder()
                .reference(invalidBalanceRef)
                .accountNumber("NLTEST2")
                .description(invalidBalanceRecordDescription)
                .startBalance(BigDecimal.ZERO)
                .mutation(BigDecimal.TEN)
                .endBalance(BigDecimal.ONE)
                .build();
        List<StatementRecord> records = List.of(validBalanceRecord, invalidBalanceRecord);

        List<InvalidRecord> invalidRecords = StatementValidator.validate(records);

        assertTrue(!invalidRecords.isEmpty());
        assertEquals(invalidBalanceRef, invalidRecords.get(0).reference());
        assertEquals(invalidBalanceRecordDescription, invalidRecords.get(0).description());
    }

    @Test
    void StatementValidator_WhenDuplicateReference_CorrectFailures() {
        Long duplicateRef = 77612L;
        StatementRecord firstRecord = StatementRecord.builder()
                .reference(duplicateRef)
                .accountNumber("NLTEST")
                .description("A transaction")
                .startBalance(BigDecimal.ZERO)
                .mutation(BigDecimal.TEN)
                .endBalance(BigDecimal.TEN)
                .build();
        StatementRecord secondRecord = StatementRecord.builder()
                .reference(duplicateRef)
                .accountNumber("NLTEST2")
                .description("Another transaction")
                .startBalance(BigDecimal.ZERO)
                .mutation(BigDecimal.TEN)
                .endBalance(BigDecimal.TEN)
                .build();
        StatementRecord thirdRecord = StatementRecord.builder()
                .reference(88933L)
                .accountNumber("NLTEST7")
                .description("Some other transaction")
                .startBalance(BigDecimal.ZERO)
                .mutation(BigDecimal.TEN)
                .endBalance(BigDecimal.TEN)
                .build();
        List<StatementRecord> records = List.of(firstRecord, secondRecord, thirdRecord);

        List<InvalidRecord> invalidRecords = StatementValidator.validate(records);

        invalidRecords.stream()
                .map(InvalidRecord::reference)
                .forEach(reference -> assertEquals(duplicateRef, reference));
    }
}
