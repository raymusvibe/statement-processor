package com.rabobank.statement.processor.config;

import com.rabobank.statement.processor.dto.GenericMessage;
import com.rabobank.statement.processor.exception.StatementValidationException;
import com.rabobank.statement.processor.exception.UnsupportedFileTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler({StatementValidationException.class, UnsupportedFileTypeException.class})
    @ResponseBody
    public ResponseEntity<GenericMessage> handleValidationException(Exception ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<GenericMessage> buildResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(new GenericMessage(message), status);
    }
}
