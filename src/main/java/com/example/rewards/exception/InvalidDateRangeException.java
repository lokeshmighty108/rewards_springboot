package com.example.rewards.exception;

/**
 * Thrown when a date range request has an invalid combination of start and end dates.
 */
public class InvalidDateRangeException extends RuntimeException {

    /**
     * Creates an invalid date range exception.
     *
     * @param message exception message
     */
    public InvalidDateRangeException(String message) {
        super(message);
    }
}
