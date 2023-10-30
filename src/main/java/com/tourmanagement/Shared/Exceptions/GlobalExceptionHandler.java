package com.tourmanagement.Shared.Exceptions;

import com.tourmanagement.Shared.Types.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(BindException ex) {
        BindingResult result = ex.getBindingResult();
        String errorMessage = result.getFieldError().getDefaultMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), errorMessage);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException responseStatusException) {
        ErrorResponse errorResponse = new ErrorResponse(responseStatusException.getStatusCode().value()
                , HttpStatus.valueOf(responseStatusException.getStatusCode().value()).name()
                , responseStatusException.getReason());

        return ResponseEntity.status(responseStatusException.getStatusCode()).body(errorResponse);
    }
}
