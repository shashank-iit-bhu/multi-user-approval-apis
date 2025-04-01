package com.assignment.user_approval.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    public static String generateKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256-bit key for strong security
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }
}
