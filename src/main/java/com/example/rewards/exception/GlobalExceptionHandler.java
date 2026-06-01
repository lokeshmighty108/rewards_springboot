package com.example.rewards.exception;

import com.example.rewards.dto.ApiErrorResponse;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Maps application exceptions to consistent API error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles customer not found errors.
     *
     * @param exception source exception
     * @param request web request
     * @return error response
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomerNotFound(
            CustomerNotFoundException exception,
            ServletWebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequest().getRequestURI());
    }

    /**
     * Handles invalid date range errors.
     *
     * @param exception source exception
     * @param request web request
     * @return error response
     */
    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidDateRange(
            InvalidDateRangeException exception,
            ServletWebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request.getRequest().getRequestURI());
    }

    /**
     * Handles invalid transaction data errors.
     *
     * @param exception source exception
     * @param request web request
     * @return error response
     */
    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidTransaction(
            InvalidTransactionException exception,
            ServletWebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request.getRequest().getRequestURI());
    }

    /**
     * Handles invalid query parameter data type errors.
     *
     * @param exception source exception
     * @param request web request
     * @return error response
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception,
            ServletWebRequest request) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid parameter value: " + exception.getValue(),
                request.getRequest().getRequestURI());
    }

    /**
     * Handles all remaining uncaught exceptions.
     *
     * @param exception source exception
     * @param request web request
     * @return error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception exception,
            ServletWebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", request.getRequest().getRequestURI());
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String message, String path) {
        ApiErrorResponse response = new ApiErrorResponse(Instant.now(), status.value(), status.getReasonPhrase(), message, path);
        return ResponseEntity.status(status).body(response);
    }
}
