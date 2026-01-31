package com.authentication.exception.handle;

import com.authentication.dto.response.ApiResponse;
import com.authentication.exception.TokenAlreadyExpiredException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ApiResponse<Map<String, String>> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.toString(),
                400,
                "Validation failed",
                true,
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenAlreadyExpiredException.class)
    public ResponseEntity<ApiResponse<Void>> handleTokenAlreadyExpiredException(TokenAlreadyExpiredException ex) {
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.GONE.toString(),
                410,
                ex.getMessage(),
                true,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.GONE);
    }


    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityExistsException(EntityExistsException e){
        ApiResponse<Void> apiResponse =new  ApiResponse(
                HttpStatus.CONFLICT.toString(),
                409,
                e.getMessage(),
                true,
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFoundException(EntityNotFoundException e){
        ApiResponse<Void> apiResponse =new  ApiResponse(
                HttpStatus.NOT_FOUND.toString(),
                404,
                e.getMessage(),
                true,
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
}
