package ru.hofftech.liga.lessons.billing.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.hofftech.liga.lessons.billing.model.dto.ApiError;

import java.util.List;

@RestControllerAdvice
public class ResponseControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                ApiError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(List.of(ex.getMessage()))
                        .build(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                ApiError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(List.of(ex.getMessage()))
                        .build(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }
}
