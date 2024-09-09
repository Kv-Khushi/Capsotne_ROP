package com.users.passwordencryption;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Utility class for encoding and decoding passwords using Base64 encoding.
 * <p>
 * This class provides static methods to encode and decode passwords.
 * Passwords are encoded into a Base64 format to be safely stored or transmitted,
 * and can be decoded back to their original form when needed.
 * </p>
 */
public class PasswordEncodingAndDecoding {

    /**
     * Encodes the given password into Base64 format.
     * <p>
     * This method converts the input password into a byte array and then encodes it using Base64 encoding.
     * The encoded password can be safely stored or transmitted as a Base64 string.
     * </p>
     *
     * @param password the plain text password to be encoded
     * @return the Base64 encoded string of the password
     */
    public static String encodePassword(final String password) {
        return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decodes the given Base64 encoded password back to its original form.
     * <p>
     * This method decodes the Base64 encoded string into a byte array and then converts it back into a plain text string.
     * </p>
     *
     * @param encodedPassword the Base64 encoded password string to be decoded
     * @return the decoded plain text password
     */
    public static String decodePassword(final String encodedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}

