package com.swag.utils;

import com.swag.dto.HttpResponseDTO;

public class Utility {

    private Utility() {
        throw new IllegalStateException("Utility class");
    }


    public static HttpResponseDTO setResponseCodeAndMessage(final HttpResponseDTO httpResponseDTO, final int responseCode, final String responseMessage) {
        httpResponseDTO.setResponseCode(responseCode);
        httpResponseDTO.setResponseMessage(responseMessage);
        return httpResponseDTO;
    }

    public static HttpResponseDTO setResponseCodeAndMessageWithBody(final HttpResponseDTO httpResponseDTO, final int responseCode, final String responseMessage, final Object responseBody) {
        httpResponseDTO.setResponseCode(responseCode);
        httpResponseDTO.setResponseMessage(responseMessage);
        httpResponseDTO.setResponseBody(responseBody);
        return httpResponseDTO;
    }
    public static boolean isNullOrEmpty(String str1, String str2){
        return (str1 == null || str1.trim().isEmpty()) ||
                (str2 == null || str2.trim().isEmpty()) ;
    }
}