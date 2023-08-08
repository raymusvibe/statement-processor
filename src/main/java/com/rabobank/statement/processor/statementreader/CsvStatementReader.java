package com.rabobank.statement.processor.statementreader;

import com.opencsv.bean.CsvToBeanBuilder;
import com.rabobank.statement.processor.exception.StatementValidationException;
import com.rabobank.statement.processor.model.StatementRecord;
import com.rabobank.statement.processor.model.csv.CsvStatementRecord;
import com.rabobank.statement.processor.statementreader.abstractions.StatementReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.modelmapper.ModelMapper;

public class CsvStatementReader implements StatementReader {

    private final ModelMapper modelMapper;

    public CsvStatementReader() {
        modelMapper = new ModelMapper();
    }

    @Override
    public List<StatementRecord> readRecords(InputStream inputStream) {
        CsvToBeanBuilder<CsvStatementRecord> beanBuilder =
                new CsvToBeanBuilder<>(new BufferedReader(new InputStreamReader(inputStream)));

        List<CsvStatementRecord> records;
        try {
            records = beanBuilder
                    .withOrderedResults(false)
                    .withType(CsvStatementRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build()
                    .parse();
        } catch (RuntimeException e) {
            throw new StatementValidationException(e);
        }

        return records.parallelStream().map(this::mapToStatementRecord).toList();
    }

    private StatementRecord mapToStatementRecord(CsvStatementRecord statementRecord) {
        return modelMapper.map(statementRecord, StatementRecord.class);
    }
}
