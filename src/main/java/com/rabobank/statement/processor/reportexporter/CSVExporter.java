package com.rabobank.statement.processor.reportexporter;

import static com.opencsv.ICSVWriter.DEFAULT_SEPARATOR;
import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.rabobank.statement.processor.dto.InvalidRecord;
import com.rabobank.statement.processor.exception.ReportExporterException;
import com.rabobank.statement.processor.reportexporter.abstractions.ReportExporter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CSVExporter implements ReportExporter {
    @Override
    public void writeFailedRecords(List<InvalidRecord> failedRecords, Writer writer) {
        try {
            String headerLine = Arrays.stream(InvalidRecord.class.getDeclaredFields())
                    .map(field -> field.getAnnotation(CsvBindByName.class))
                    .filter(Objects::nonNull)
                    .map(CsvBindByName::column)
                    .collect(Collectors.joining(","));

            StringReader stringReader = new StringReader(headerLine);
            CSVReader reader = new CSVReader(stringReader);

            HeaderColumnNameMappingStrategy<InvalidRecord> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(InvalidRecord.class);
            strategy.captureHeader(reader);

            StatefulBeanToCsv<InvalidRecord> csvWriter = new StatefulBeanToCsvBuilder<InvalidRecord>(writer)
                    .withQuotechar(NO_QUOTE_CHARACTER)
                    .withSeparator(DEFAULT_SEPARATOR)
                    .withMappingStrategy(strategy)
                    .withOrderedResults(false)
                    .build();

            csvWriter.write(failedRecords);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
            throw new ReportExporterException(e);
        }
    }
}
