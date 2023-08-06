package com.rabobank.statement.validator.model;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementRecord {
    private Long reference;
    private String accountNumber;
    private String description;
    private BigDecimal startBalance;
    private BigDecimal mutation;
    private BigDecimal endBalance;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StatementRecord that)) {
            return false;
        }

        return Objects.equals(reference, that.reference);
    }

    @Override
    public int hashCode() {
        return reference != null ? reference.hashCode() : 0;
    }
}
