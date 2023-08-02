package com.rabobank.statement.processor.model.csv;

import com.opencsv.bean.CsvBindByName;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CsvStatementRecord {
    @CsvBindByName(required = true)
    private Long reference;

    @CsvBindByName(column = "Account Number")
    private String accountNumber;

    @CsvBindByName(required = true)
    private String description;

    @CsvBindByName(column = "Start Balance", required = true)
    private BigDecimal startBalance;

    @CsvBindByName(required = true)
    private BigDecimal mutation;

    @CsvBindByName(column = "End Balance", required = true)
    private BigDecimal endBalance;
}
