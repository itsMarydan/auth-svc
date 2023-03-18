package com.blueint.authsvc.utils;


import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32]; // 256 bits
        random.nextBytes(bytes);
        String token = Base64.getEncoder().encodeToString(bytes);
        return token;
    }
}
