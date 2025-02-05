package com.sportsevents.backend.sportseventsbackend.common.exception;

import com.sportsevents.backend.sportseventsbackend.user.exception.RegistrationException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::getErrorMassage)
                .toList();

        Map<String, Object> body = getBodyMassage(errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, Object> body = getBodyMassage(List.of(ex.getMessage()), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<Object> handleRegistrationException(RegistrationException ex) {
        Map<String, Object> body = getBodyMassage(List.of(ex.getMessage()), HttpStatus.CONFLICT);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, Object> body = getBodyMassage(List.of("Invalid username or password"),
                HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> handleJwtException(JwtException ex) {
        Map<String, Object> body = getBodyMassage(List.of(ex.getMessage()), HttpStatus.UNAUTHORIZED );
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> body = getBodyMassage(List.of(ex.getMessage() + "The JWT token have invalid format"), HttpStatus.UNAUTHORIZED );
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<Object> handleLockedException(LockedException ex) {
        Map<String, Object> body = getBodyMassage(List.of("Account is locked"), HttpStatus.LOCKED);
        return new ResponseEntity<>(body, HttpStatus.LOCKED);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Object> handleDisabledException(DisabledException ex) {
        Map<String, Object> body = getBodyMassage(List.of("Account is disabled"),
                HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    private String getErrorMassage(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            String field = fieldError.getField();
            String massage = error.getDefaultMessage();
            return field + ": " + massage;
        }
        return error.getDefaultMessage();
    }

    private Map<String, Object> getBodyMassage(List<String> errors, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        body.put("errors", errors);
        return body;
    }
}
