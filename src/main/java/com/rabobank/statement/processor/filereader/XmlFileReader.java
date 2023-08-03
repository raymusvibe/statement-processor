package com.rabobank.statement.processor.filereader;

import com.rabobank.statement.processor.exception.StatementValidationException;
import com.rabobank.statement.processor.filereader.abstractions.FileReader;
import com.rabobank.statement.processor.model.StatementRecord;
import com.rabobank.statement.processor.model.xml.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

@Component
public class XmlFileReader implements FileReader {
    private final ModelMapper modelMapper;

    public XmlFileReader() {
        modelMapper = new ModelMapper();
    }

    @Override
    public List<StatementRecord> readRecords(InputStream inputStream) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlRecordsRoot.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema statementSchema = schemaFactory.newSchema(getSchemaFile());
            jaxbUnmarshaller.setSchema(statementSchema);

            XmlRecordsRoot xmlRecordsRoot = (XmlRecordsRoot) jaxbUnmarshaller.unmarshal(inputStream);

            return xmlRecordsRoot.getRecordList().parallelStream()
                    .map(this::mapToStatementRecord)
                    .toList();
        } catch (JAXBException | SAXException e) {
            throw new StatementValidationException(e);
        }
    }

    private File getSchemaFile() {
        URL resource = getClass().getClassLoader().getResource("xsd/statement.xsd");
        Objects.requireNonNull(resource);
        return new File(resource.getPath());
    }

    private StatementRecord mapToStatementRecord(XmlStatementRecord statementRecord) {
        return modelMapper.map(statementRecord, StatementRecord.class);
    }
}
