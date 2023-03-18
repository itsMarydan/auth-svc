package com.blueint.authsvc.utils;

public class StringUtils {
    public static String cleanString(String input) {
        String clean = input.replaceAll("[^a-zA-Z0-9\\s-]+", "");
        clean = clean.replaceAll("\\s+", "-");
        return clean;
    }
}
