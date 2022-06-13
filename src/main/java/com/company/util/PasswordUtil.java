package com.company.util;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Random;

@Slf4j
public class PasswordUtil {

    private static final char[] UPPER = "ABCDEFGJKLMNPRSTUVWXYZ".toCharArray();

    private static final char[] LOWER = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final char[] NUMBER = "0123456789".toCharArray();

    private static final char[] ALL = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789".toCharArray();

    private static final int DEFAULT_LENGTH = 8;

    public static String generatePassword() {
        return generate(DEFAULT_LENGTH);
    }

    public static String generatePassword(int length) {
        return generate(length);
    }

    private static String generate(int length) {
        log.info("Generate Password Starts length={}", length);

        Random random = new SecureRandom();

        StringBuilder password = new StringBuilder(length);

        if (length > 4) {
            for (int i = 0; i < length - 4; i++) {
                password.append(ALL[random.nextInt(ALL.length)]);
            }
        }else {
            password.append(ALL[random.nextInt(ALL.length)]);
        }

        password.insert(random.nextInt(password.length()), UPPER[random.nextInt(UPPER.length)]);
        password.insert(random.nextInt(password.length()), LOWER[random.nextInt(LOWER.length)]);
        password.insert(random.nextInt(password.length()), NUMBER[random.nextInt(NUMBER.length)]);
        password.insert(random.nextInt(password.length()), ALL[random.nextInt(ALL.length)]);

        log.info("Generate Password Finished password={}", password);
        return password.toString();
    }

}
