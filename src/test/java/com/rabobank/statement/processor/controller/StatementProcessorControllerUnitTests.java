package com.rabobank.statement.processor.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rabobank.statement.processor.exception.StatementValidationException;
import com.rabobank.statement.processor.exception.UnsupportedFileTypeException;
import com.rabobank.statement.processor.service.abstractions.StatementValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(StatementProcessorController.class)
public class StatementProcessorControllerUnitTests {
    @MockBean
    private StatementValidationService validationService;

    @Autowired
    MockMvc mockMvc;

    private final Resource valid_records = new ClassPathResource("xml/valid_records.xml");
    private final Resource malformed_records = new ClassPathResource("xml/malformed_records.xml");
    private final Resource unsupported_format_records = new ClassPathResource("unsupported_format/json_format.json");

    @Test
    void StatementProcessorController_WhenValidUpload_200Response() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "xml/valid_records.xml", "text/xml", valid_records.getInputStream());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/validate")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void StatementProcessorController_WhenInvalidUpload_400Response() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "xml/malformed_records.xml", "text/xml", malformed_records.getInputStream());
        Mockito.when(validationService.validateStatementFile(file))
                .thenThrow(new StatementValidationException("Invalid data"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/validate")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void StatementProcessorController_WhenUnsupportedFileFormat_400Response() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "unsupported_format/json_format.json",
                "text/json",
                unsupported_format_records.getInputStream());
        Mockito.when(validationService.validateStatementFile(file))
                .thenThrow(new UnsupportedFileTypeException("File type not supported"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/validate")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
