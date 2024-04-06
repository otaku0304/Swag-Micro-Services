package com.swagger.utils;

import com.swagger.entity.GenericDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

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
}
