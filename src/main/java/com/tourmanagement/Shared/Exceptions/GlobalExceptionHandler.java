package com.tourmanagement.Shared.Exceptions;

import com.tourmanagement.Shared.Types.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException responseStatusException) {
        ErrorResponse errorResponse = new ErrorResponse(responseStatusException.getStatusCode().value()
                , HttpStatus.valueOf(responseStatusException.getStatusCode().value()).name()
                , responseStatusException.getReason());

        return ResponseEntity.status(responseStatusException.getStatusCode()).body(errorResponse);
    }
}
