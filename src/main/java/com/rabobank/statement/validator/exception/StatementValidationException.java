package com.rabobank.statement.validator.exception;

public class StatementValidationException extends RuntimeException {
    public StatementValidationException(String message) {
        super(message);
    }

    public StatementValidationException(Throwable throwable) {
        super(throwable);
    }
}
