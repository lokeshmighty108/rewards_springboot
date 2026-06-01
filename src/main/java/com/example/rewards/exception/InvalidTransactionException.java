package com.example.rewards.exception;

/**
 * Thrown when a transaction contains invalid data for reward calculation.
 */
public class InvalidTransactionException extends RuntimeException {

    /**
     * Creates an invalid transaction exception.
     *
     * @param message exception message
     */
    public InvalidTransactionException(String message) {
        super(message);
    }
}
