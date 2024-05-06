package org.example.util.validation;

public class PhoneNumberValidator {

    public static boolean isIranianPhoneNumberValid(String phoneNumber) {
        // Remove any non-numeric characters from the phone number
        String numericPhoneNumber = phoneNumber.replaceAll("[^\\d]", "");

        // Check if the numeric phone number starts with the correct country code
        if (!numericPhoneNumber.startsWith("98")) {
            return false;
        }

        // Check the length of the numeric phone number
        if (numericPhoneNumber.length() != 12) {
            return false;
        }

        // Additional validation based on specific rules for Iranian phone numbers
        // You can customize this part based on your requirements

        // Example: Check if the third digit is within a specific range
        int thirdDigit = Character.getNumericValue(numericPhoneNumber.charAt(2));
        if (thirdDigit < 1 || thirdDigit > 9) {
            return false;
        }

        // Add more specific validation rules as needed

        return true;
    }
}

