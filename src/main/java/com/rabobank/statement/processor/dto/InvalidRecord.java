package com.rabobank.statement.processor.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvalidRecord {
    @CsvBindByName(column = "Reference", required = true)
    private Long reference;

    @CsvBindByName(column = "Description", required = true)
    private String description;
}
