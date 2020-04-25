package com.techm.diway.counterapp.error.handlers;

import com.techm.diway.counterapp.error.exceptions.AppRuntimeException;
import com.techm.diway.counterapp.models.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppRuntimeException.class)
    protected ResponseEntity<ErrorResponse> appRuntimeException(WebRequest request, AppRuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .setMessage(ex.getMessage())
                .setStatus(ex.getErrorCodes().getCode());

        return new ResponseEntity(errorResponse, ex.getErrorCodes().getStatus());
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse()
                .setMessage("Invalid request body")
                .setStatus(status.value());
        return new ResponseEntity(errorResponse, status);
    }
}
