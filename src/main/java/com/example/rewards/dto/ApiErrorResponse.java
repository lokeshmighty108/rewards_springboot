package com.example.rewards.dto;

import java.time.Instant;

/**
 * Standard API error payload.
 */
public class ApiErrorResponse {

    private final Instant timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    /**
     * Creates an API error response.
     *
     * @param timestamp error timestamp
     * @param status HTTP status code
     * @param error HTTP status reason phrase
     * @param message business or validation message
     * @param path request path
     */
    public ApiErrorResponse(Instant timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
