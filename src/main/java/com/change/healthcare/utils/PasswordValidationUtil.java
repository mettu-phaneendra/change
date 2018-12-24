package com.change.healthcare.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidationUtil implements ValidationUtil {

    private boolean alphaNumericValidation(String password) {
        String regex = "^(?=.*\\d)(?=.*[a-z])[a-z0-9]*$";
        return validate(password, regex);
    }

    private boolean lengthValidation(String password) {
        String regex = ".{5,12}";
        return validate(password, regex);
    }

    private boolean sequenceValidation(String password) {
        String regex = "(?!(.+?)\\1).*";
        return validate(password, regex);
    }

    private Boolean validate(String password, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public Map<String, Boolean> validate(String password) {
        Map<String, Boolean> results = new HashMap<>();
        results.put(PasswordValidationType.ALPHA_NUMERIC.getType(), alphaNumericValidation(password));
        results.put(PasswordValidationType.LENGTH.getType(), lengthValidation(password));
        results.put(PasswordValidationType.SEQUENCE.getType(), sequenceValidation(password));

        return results;
    }

}
