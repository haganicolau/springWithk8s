package com.unilabs.newschedule.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * An abstract class that implements part of the responses with some messages to be delivered in the request.
 */
public abstract class AbstractController {

    /**
     * It Handles messages for a bad request.
     * @param message Message to be delivered in the request
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> getBadRequestMessage(String message) {
        String stringConcat = String.format("{\"message\": \"%s\"}", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(stringConcat);
    }

    /**
     * It Handles messages for a error request.
     * @param message Message to be delivered in the request
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> getErrorMessage(String message) {
        String stringConcat = String.format("{\"message\": \"%s\"}", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(stringConcat);
    }

    /**
     * It Handles messages for a success request.
     * @param message Message to be delivered in the request
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> getSuccessMessage(String message) {
        String stringConcat = String.format("{\"message\": \"%s\"}", message);
        return ResponseEntity.ok().body(stringConcat);
    }

    /**
     * It Handles messages for a not found request.
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> getNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
