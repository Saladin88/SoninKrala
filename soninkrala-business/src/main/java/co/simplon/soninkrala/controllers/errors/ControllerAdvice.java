package co.simplon.soninkrala.controllers.errors;

import co.simplon.soninkrala.dtos.validators.CustomErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    final Map<String, String> errors = new HashMap<>();

    @Override //Gestion des erreurs au niveau des DTOs sur des requestbody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, ArrayList<String>> fieldErrors = new HashMap<>();
        for(FieldError error : ex.getFieldErrors()) {
            String fieldName = error.getField();
            String code = error.getCode();
            fieldErrors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(code);
        }
        CustomErrorResponse customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setFieldErrors(fieldErrors);
        return handleExceptionInternal(ex, customErrorResponse, headers, status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class) // Gestion des erreurs dans les pathVariable ; RequestParam, Validated
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex) {
        Map<String, ArrayList<String>> fieldErrors = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            String fieldName = error.getPropertyPath().toString();
            String errorMessage = error.getMessage();

            fieldErrors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        });
        CustomErrorResponse customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setFieldErrors(fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(AccountAlreadyExistsErrorMessage.class)
    public ResponseEntity<Object> accountAlreadyExistsErrorHandler(AccountAlreadyExistsErrorMessage error) {
        return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FetchAllDataQuizErrorMessage.class)
    public ResponseEntity<Object> fetchAllDataQuizErrorHandler(FetchAllDataQuizErrorMessage error) {
        return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    };

    @ExceptionHandler(QuizErrorMessage.class)
    protected ResponseEntity<Object> handleQuizErrorHandler(QuizErrorMessage error) {
        return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
