package com.swag.globalexceptionshandler;

public class SwagServiceException extends RuntimeException {

    public SwagServiceException(String message) {
        super(message);
    }

    public SwagServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
