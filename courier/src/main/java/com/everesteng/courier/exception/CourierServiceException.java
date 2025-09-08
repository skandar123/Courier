/*
 * Name: Sayantika Kandar
 * Purpose: Custom exception class for the Courier Service application.
 * This class extends Java's built-in Exception class to represent
 * application-specific errors with meaningful messages.
 */

package com.everesteng.courier.exception;

public class CourierServiceException extends Exception {

    /**
     * Constructor for CourierServiceException.
     *
     * @param message descriptive error message to explain the cause of the exception
     */
    public CourierServiceException(String message) {
        // Call the parent Exception class constructor with the provided message
        super(message);
    }
}