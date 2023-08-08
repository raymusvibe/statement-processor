package com.rabobank.statement.processor.model.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Setter;

@XmlRootElement(name = "records")
public class XmlRecordsRoot {
    @Setter
    private List<XmlStatementRecord> recordList = new ArrayList<>();

    @XmlElement(name = "record")
    public List<XmlStatementRecord> getRecordList() {
        return recordList;
    }
}
