package com.rabobank.statement.validator.model.xml;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
public class XmlStatementRecord {
    private Long reference;

    private String accountNumber;

    private String description;

    private BigDecimal startBalance;

    private BigDecimal mutation;

    private BigDecimal endBalance;

    @XmlAttribute
    public Long getReference() {
        return reference;
    }
}
