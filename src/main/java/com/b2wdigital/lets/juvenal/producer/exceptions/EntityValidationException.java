package com.b2wdigital.lets.juvenal.producer.exceptions;

public class EntityValidationException extends Exception{

    private final String message;

    public EntityValidationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
