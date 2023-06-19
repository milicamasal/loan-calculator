package rs.masal.milica.loancalculator.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import rs.masal.milica.loancalculator.domain.validation.ValidationErrorResponse;
import rs.masal.milica.loancalculator.domain.validation.Violation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;


@RestControllerAdvice
@Slf4j
class GeneralExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException ex) {
        log.error("onConstraintValidationException ", ex);
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            errorResponse.getViolations().add(Violation.builder()
                    .fieldName(violation.getPropertyPath().toString())
                    .message(violation.getMessage()).build());
        }
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorResponse.setTimestamp(new Date());
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("onMethodArgumentNotValidException ", ex);
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.getViolations().add(Violation.builder()
                    .fieldName(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build());
        }
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorResponse.setTimestamp(new Date());
        return errorResponse;
    }
}
