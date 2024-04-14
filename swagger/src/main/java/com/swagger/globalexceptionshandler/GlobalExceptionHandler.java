package com.swagger.globalexceptionshandler;


import com.swagger.dto.HttpResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpResponseDTO> handleInternalServerError(Exception ex) {
        String errorMessage = "Internal server error occurred";
        log.error(errorMessage, ex);
        HttpResponseDTO responseDTO = new HttpResponseDTO();
        responseDTO.setResponseMessage(errorMessage);
        responseDTO.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseDTO);
    }

    @ExceptionHandler(SwaggerServiceException.class)
    public ResponseEntity<HttpResponseDTO> handleSwaggestServiceException(SwaggerServiceException ex) {
        log.error("Error occurred in Swaggest service: {}", ex.getMessage());
        HttpResponseDTO responseDTO = new HttpResponseDTO();
        responseDTO.setResponseMessage(ex.getMessage());
        responseDTO.setResponseCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseDTO);
    }
}
