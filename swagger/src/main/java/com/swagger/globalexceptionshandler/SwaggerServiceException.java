package com.swagger.globalexceptionshandler;

public class SwaggerServiceException extends RuntimeException {

    public SwaggerServiceException(String message) {
        super(message);
    }

    public SwaggerServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
