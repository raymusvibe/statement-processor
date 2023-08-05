package com.rabobank.statement.processor.exception;

public class ReportWriteException extends RuntimeException {
    public ReportWriteException(Throwable throwable) {
        super(throwable);
    }
}
