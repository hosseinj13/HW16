package org.example.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    // Minimum length for a strong password
    private static final int MIN_LENGTH = 8;

    // At least one uppercase letter
    private static final String UPPERCASE_REGEX = ".*[A-Z].*";

    // At least one lowercase letter
    private static final String LOWERCASE_REGEX = ".*[a-z].*";

    // At least one digit
    private static final String DIGIT_REGEX = ".*\\d.*";

    // At least one special character
    private static final String SPECIAL_CHAR_REGEX = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*";

    public static boolean isPasswordValid(String password) {
        return password != null &&
                password.length() >= MIN_LENGTH &&
                password.matches(UPPERCASE_REGEX) &&
                password.matches(LOWERCASE_REGEX) &&
                password.matches(DIGIT_REGEX) &&
                password.matches(SPECIAL_CHAR_REGEX);
    }
}