package com.swag.GlobalExceptionsHandler;

public class SwaggestServiceException extends RuntimeException {

    public SwaggestServiceException(String message) {
        super(message);
    }

    public SwaggestServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
