package com.change.healthcare.utils;

public enum PasswordValidationType {
    ALPHA_NUMERIC("alpha-numeric-check"),
    LENGTH("length-check"),
    SEQUENCE("sequence-check");

    private final String type;

    PasswordValidationType(String type) {
        this.type = type;
    }

    public static PasswordValidationType fromString(String type) {
        if (null == type) {
            return null;
        }
        for (PasswordValidationType c : PasswordValidationType.values()) {
            if (type.equalsIgnoreCase(c.type)) {
                return c;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }
}
