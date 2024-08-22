package com.users.passwordencryption;

import org.springframework.stereotype.Service;

import java.util.Base64;


public class PasswordEncodingAndDecoding {

    public static String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static String decodePassword(String encodedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
        return new String(decodedBytes);
    }
}
