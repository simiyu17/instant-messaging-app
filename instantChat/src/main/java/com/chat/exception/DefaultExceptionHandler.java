package com.chat.exception;

import com.chat.dto.ApiErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorMessage> defaultException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorMessage(false, ex.getMessage(), HttpStatus.NOT_FOUND.value(), request.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<ApiErrorMessage> handleAuthenticationException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorMessage(false, ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), request.getDescription(false)), HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorMessage> resourceNotFoundException(UserNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorMessage(false, ex.getMessage(), HttpStatus.NOT_FOUND.value(), request.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DuplicateUserNameException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ApiErrorMessage> duplicateUserNameException(DuplicateUserNameException ex, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorMessage(false, ex.getMessage(), HttpStatus.CONFLICT.value(), request.getDescription(false)), HttpStatus.NOT_FOUND);
    }
}
