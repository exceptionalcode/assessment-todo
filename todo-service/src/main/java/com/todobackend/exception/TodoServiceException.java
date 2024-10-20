package com.todobackend.exception;

/**
 * Custom exception class for TodoService errors.
 * This class extends RuntimeException and is used to indicate
 * any service-level failures.
 */
public class TodoServiceException extends RuntimeException {

    public TodoServiceException(String message) {
        super(message);
    }

    public TodoServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
