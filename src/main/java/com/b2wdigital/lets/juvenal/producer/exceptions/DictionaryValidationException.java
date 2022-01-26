package com.b2wdigital.lets.juvenal.producer.exceptions;

public class DictionaryValidationException extends Exception {

    private final String message;

    public DictionaryValidationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
