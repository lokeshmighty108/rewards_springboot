package com.example.rewards.exception;

/**
 * Thrown when a request parameter is syntactically valid but not acceptable for the API.
 */
public class InvalidRequestException extends RuntimeException {

    /**
     * Creates an invalid request exception.
     *
     * @param message exception message
     */
    public InvalidRequestException(String message) {
        super(message);
    }
}
