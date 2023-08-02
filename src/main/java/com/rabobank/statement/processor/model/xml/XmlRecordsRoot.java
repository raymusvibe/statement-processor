package com.rabobank.statement.processor.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "records")
public class XmlRecordsRoot {
    @JacksonXmlProperty(localName = "record")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<XmlStatementRecord> recordsList = new ArrayList<>();
}
