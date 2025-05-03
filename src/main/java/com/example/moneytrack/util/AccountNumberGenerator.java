package com.example.moneytrack.util;

public class AccountNumberGenerator {
    public static String generate(String bankCode, String productCode, String recentAccountNumber) {
        int nextSequence = 1;

        if (recentAccountNumber != null && recentAccountNumber.length() == 14) {
            String recentSeq = recentAccountNumber.substring(6);
            nextSequence = Integer.parseInt(recentSeq) + 1;
        }

        String sequencePart = String.format("%08d", nextSequence);

        return bankCode + productCode + sequencePart;
    }
}
