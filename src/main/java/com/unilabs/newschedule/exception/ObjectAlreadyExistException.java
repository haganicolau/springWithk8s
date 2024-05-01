package com.unilabs.newschedule.exception;

/**
 * An exception should be thrown when an item with the same property (Room code, exam code, or patient utente)
 * is already registered.
 */
public class ObjectAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ObjectAlreadyExistException(String message) {
        super(message);
    }

    public ObjectAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
