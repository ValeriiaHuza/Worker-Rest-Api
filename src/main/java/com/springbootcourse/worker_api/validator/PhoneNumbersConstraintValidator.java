package com.springbootcourse.worker_api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PhoneNumbersConstraintValidator implements ConstraintValidator<ValidPhoneNumbers, Set<String>> {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?[0-9]{9,13}$");


    @Override
    public boolean isValid(Set<String> phoneNumbers, ConstraintValidatorContext context) {
        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            return true; // Consider empty set as valid, or return false if it's not allowed
        }

        Set<String> invalidNumbers = phoneNumbers.stream()
                .filter(phone -> !PHONE_PATTERN.matcher(phone).matches())
                .collect(Collectors.toSet());

        if (!invalidNumbers.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid phone number(s): " + String.join(", ", invalidNumbers))
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}