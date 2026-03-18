package com.taidt9.productservice.exception;

import com.taidt9.productservice.api.ApiError;
import com.taidt9.productservice.api.ApiResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {

        ApiError error = ApiError.builder()
                .code("RESOURCE_NOT_FOUND")
                .message(ex.getMessage())
                .build();

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .error(error)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> validationErrors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ApiError error = ApiError.builder()
                .code("VALIDATION_ERROR")
                .message("Invalid request")
                .details(validationErrors)
                .build();

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .error(error)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {

        ApiError error = ApiError.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("Unexpected error occurred")
                .build();

        log.error("Exception caught in Product Service: {}", ex.getMessage(), ex);

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .error(error)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}