package com.rabobank.statement.processor.dto;

import java.util.List;

public record ValidationResult(List<InvalidRecord> invalidRecords) {}
