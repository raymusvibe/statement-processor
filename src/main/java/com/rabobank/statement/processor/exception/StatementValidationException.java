package com.rabobank.statement.processor.exception;

public class StatementValidationException extends RuntimeException {
    public StatementValidationException(String message) {
        super(message);
    }

    public StatementValidationException(Throwable throwable) {
        super(throwable);
    }
}
