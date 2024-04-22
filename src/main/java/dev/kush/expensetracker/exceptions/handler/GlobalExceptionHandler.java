package dev.kush.expensetracker.exceptions.handler;

import dev.kush.expensetracker.exceptions.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // TODO: Do proper exception handling

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorMessage handleIllegalArgumentException(IllegalArgumentException ex) {
//        return new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorMessage handleAuthenticationException(AuthenticationException ex) {
//        return new ErrorMessage(ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), System.currentTimeMillis());
//    }
}
