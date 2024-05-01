package com.unilabs.newschedule.exception;

/**
 * An exception should be thrown when there is a schedule conflict between the created availabilities.
 */
public class DateTimeConflictException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DateTimeConflictException(String message) {
        super(message);
    }

    public DateTimeConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
