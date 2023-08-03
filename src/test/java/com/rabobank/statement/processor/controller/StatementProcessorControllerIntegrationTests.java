package com.rabobank.statement.processor.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.rabobank.statement.processor.dto.ValidationResult;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatementProcessorControllerIntegrationTests {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private static RestTemplate restTemplate;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeEach() {
        baseUrl = baseUrl + ":" + port + "/api/v1/validate";
    }

    private final Resource empty_records = new ClassPathResource("xml/empty_records.xml");
    private final Resource valid_records = new ClassPathResource("csv/valid_records.csv");
    private final Resource malformed_records = new ClassPathResource("xml/malformed_records.xml");
    private final Resource unsupported_format_records = new ClassPathResource("unsupported_format/valid_records.json");
    private final Resource invalid_records = new ClassPathResource("csv/invalid_records.csv");

    @Test
    void StatementProcessorController_WhenEmptyRecords_NoValidationFailures() throws IOException {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", empty_records);

        ValidationResult validationResult = restTemplate.postForObject(baseUrl, parts, ValidationResult.class);

        assertNotNull(validationResult);
        assertTrue(validationResult.invalidRecords().isEmpty());
    }

    @Test
    void StatementProcessorController_WhenValidRecords_NoValidationFailures() throws IOException {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", valid_records);

        ValidationResult validationResult = restTemplate.postForObject(baseUrl, parts, ValidationResult.class);

        assertNotNull(validationResult);
        assertTrue(validationResult.invalidRecords().isEmpty());
    }

    @Test
    void StatementProcessorController_WhenMalformedRecords_BadRequestException() throws IOException {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", malformed_records);

        assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForObject(baseUrl, parts, ValidationResult.class));
    }

    @Test
    void StatementProcessorController_WhenUnsupportedFormat_BadRequestException() throws IOException {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", unsupported_format_records);

        assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForObject(baseUrl, parts, ValidationResult.class));
    }

    @Test
    void StatementProcessorController_WhenInvalidRecords_CorrectFailureSize() throws IOException {
        int numberOfInvalidRecords = 3;
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", invalid_records);

        ValidationResult validationResult = restTemplate.postForObject(baseUrl, parts, ValidationResult.class);

        assertNotNull(validationResult);
        assertEquals(numberOfInvalidRecords, validationResult.invalidRecords().size());
    }
}
