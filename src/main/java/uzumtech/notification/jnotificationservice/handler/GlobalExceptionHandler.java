package uzumtech.notification.jnotificationservice.handler;


import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import uzumtech.notification.jnotificationservice.dto.error.ErrorDto;
import uzumtech.notification.jnotificationservice.exception.ApplicationException;
import uzumtech.notification.jnotificationservice.exception.DataNotFoundException;
import uzumtech.notification.jnotificationservice.exception.MerchantNotFoundException;
import uzumtech.notification.jnotificationservice.exception.NotificationNotFoundException;

import static uzumtech.notification.jnotificationservice.constant.enums.Error.*;
import static uzumtech.notification.jnotificationservice.constant.enums.ErrorType.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something is wrong:" + ex.getMessage());
    }

    @ExceptionHandler(MerchantNotFoundException.class)
    public ResponseEntity<String> handleMerchantNotFoundException(MerchantNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<String> handleNotificationNotFoundException(NotificationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    private ResponseEntity<ErrorDto> handleApplicationException(ApplicationException ex) {
        log.error("Error: {}", ex.getMessage());

        var errorBody = ErrorDto
                .builder()
                .code(ex.getCode())
                .type(ex.getErrorType())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(errorBody);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    private ResponseEntity<ErrorDto> handleMissingRequestHeaderException(final MissingRequestHeaderException ex) {
        log.error("MissingRequestHeaderException : {}", ex.getMessage(), ex);

        var error = ErrorDto
                .builder()
                .type(INTERNAL)
                .code(MISSING_REQUEST_HEADER_ERROR_CODE.getCode())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException ex) {
        log.error("HttpMediaTypeNotSupportedException : {}", ex.getMessage(), ex);

        var error = ErrorDto
                .builder()
                .type(INTERNAL)
                .code(HANDLER_NOT_FOUND_ERROR_CODE.getCode())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("HttpRequestMethodNotSupportedException : {}", ex.getMessage(), ex);

        var error = ErrorDto
                .builder()
                .type(INTERNAL)
                .code(METHOD_NOT_SUPPORTED_ERROR_CODE.getCode())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorDto> handleResourceAccessException(ResourceAccessException ex) {
        log.error("ResourceAccessException = {}", ex.getMessage(), ex);

        var error = ErrorDto
                .builder()
                .type(INTERNAL)
                .code(INTERNAL_TIMEOUT_ERROR_CODE.getCode())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("MethodArgumentTypeMismatchException = {}", ex.getMessage(), ex);

        var error = ErrorDto
                .builder()
                .type(INTERNAL)
                .code(INVALID_REQUEST_PARAM_ERROR_CODE.getCode())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage(), ex);

        var validationErrors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    } else {
                        return error.getDefaultMessage();
                    }
                })
                .toList();

        var error = ErrorDto.builder()
                .type(VALIDATION)
                .message(VALIDATION_ERROR_CODE.getMessage())
                .validationErrors(validationErrors)
                .build();

        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> httpMessageNotReadableException(final HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException : {}", ex.getMessage(), ex);

        var error = ErrorDto
                .builder()
                .type(INTERNAL)
                .code(JSON_NOT_VALID_ERROR_CODE.getCode())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException : {}", ex.getMessage(), ex);

        var error = ErrorDto.builder().build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("NoHandlerFoundException : {}", ex.getMessage(), ex);

        var error = ErrorDto
                .builder()
                .type(INTERNAL)
                .code(HANDLER_NOT_FOUND_ERROR_CODE.getCode())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<ErrorDto> handleException(final HttpClientErrorException ex) {
        log.error("HttpClientErrorException : {}", ex.getMessage(), ex);

        var error = ErrorDto
                .builder()
                .code(EXTERNAL_SERVICE_FAILED_ERROR_CODE.getCode())
                .type(INTERNAL)
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    protected ResponseEntity<ErrorDto> handleException(final HttpServerErrorException ex) {
        log.error("HttpServerErrorException : {}", ex.getMessage(), ex);

        var error = ErrorDto
                .builder()
                .code(EXTERNAL_SERVICE_FAILED_ERROR_CODE.getCode())
                .type(EXTERNAL)
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorDto> handleException(final Exception ex) {
        log.error("Exception : {}", ex.getMessage(), ex);

        var error = ErrorDto
                .builder()
                .code(INTERNAL_SERVICE_ERROR_CODE.getCode())
                .type(INTERNAL)
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
