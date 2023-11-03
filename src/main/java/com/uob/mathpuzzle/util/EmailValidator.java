package com.uob.mathpuzzle.util;

import org.springframework.stereotype.Component;

@Component
public class EmailValidator {

    private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public String getHiddenEmail(String email){

        if (email == null || email.isEmpty() || !email.matches(EMAIL_REGEX)) return null;

        String[] emailParts = email.split("@");
        String part1 = emailParts[0];
        String part2 = emailParts[1];

        return part1.substring(0, 2) + part1.substring(2).replaceAll(".","X") +"@" + part2;

    }

    public boolean isValidEmail(String email){
        return email.matches(EMAIL_REGEX);
    }
}
