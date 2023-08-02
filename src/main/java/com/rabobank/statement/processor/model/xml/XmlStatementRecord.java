package com.rabobank.statement.processor.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class XmlStatementRecord {
    @JacksonXmlProperty(isAttribute = true)
    private Long reference;

    @JacksonXmlProperty
    private String accountNumber;

    @JacksonXmlProperty
    private String description;

    @JacksonXmlProperty
    private BigDecimal startBalance;

    @JacksonXmlProperty
    private BigDecimal mutation;

    @JacksonXmlProperty
    private BigDecimal endBalance;
}
