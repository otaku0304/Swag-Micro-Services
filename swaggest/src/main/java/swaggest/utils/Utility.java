package swaggest.utils;


import org.springframework.security.crypto.bcrypt.BCrypt;
import swaggest.dto.HttpResponseDTO;
import swaggest.entity.GenericDetails;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;


public class Utility {

    private Utility() {
        throw new IllegalStateException("Utility class");
    }

    public static String base64Encode(final String code) {
        byte[] encodedBytes = Base64.getEncoder().encode(code.getBytes());
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    public static String base64Decode(final String code) {
        byte[] decodedBytes = Base64.getDecoder().decode(code.getBytes());
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }


    public static GenericDetails createGenericDetails(final String name) {
        return GenericDetails.builder()
                .createdBy(name)
                .createdTime(getCurrentTime())
                .build();
    }

    public static GenericDetails modifyGenericDetails(final String name, final GenericDetails genericDetails) {
        return GenericDetails.builder()
                .createdBy(genericDetails.getCreatedBy())
                .createdTime(genericDetails.getCreatedTime())
                .modifiedBy(name)
                .modifiedTime(getCurrentTime())
                .build();
    }


    public static Date getCurrentTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static String encryptPassword(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }


    public static boolean checkPassword(final String password, final String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static String spiltName(String name) {
        String[] fullName = name.split(" ");
        return fullName[0];
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