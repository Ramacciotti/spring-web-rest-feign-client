package com.ramacciotti.company.infrastructure.exception;

import com.ramacciotti.company.domain.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle generic error
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Define which status you want to return
    @ExceptionHandler(Exception.class)
    // Handle only the exceptions that are inside
    public ErrorResponseDTO handleGenericException(Exception exception) { // Exception we are waiting for

        // Create error message components
        String errorMessage = exception.getMessage();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        // Returns a custom message
        return new ErrorResponseDTO().withMessage(errorMessage).withStatus(httpStatus);

    }

    // If any mandatory field comes as null empty or blank
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        String errorMessage = "empty_blank_or_null_fields";
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        return new ErrorResponseDTO().withMessage(errorMessage).withStatus(httpStatus);

    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponseDTO handleRuntimeException(RuntimeException exception) { // Exception que estamos esperando

        String errorMessage = exception.getMessage();
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        return new ErrorResponseDTO().withMessage(errorMessage).withStatus(httpStatus);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ErrorResponseDTO handleNotFoundException(NotFoundException exception) {

        String errorMessage = exception.getMessage();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ErrorResponseDTO().withMessage(errorMessage).withStatus(httpStatus);

    }

}
