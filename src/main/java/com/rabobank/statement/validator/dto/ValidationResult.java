package com.rabobank.statement.validator.dto;

import java.util.List;

public record ValidationResult(List<InvalidRecord> invalidRecords) {}
