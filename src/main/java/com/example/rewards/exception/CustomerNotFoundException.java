package com.example.rewards.exception;

/**
 * Thrown when a requested customer identifier does not exist in the transaction data.
 */
public class CustomerNotFoundException extends RuntimeException {

    /**
     * Creates a customer not found exception.
     *
     * @param message exception message
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
