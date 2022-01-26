package com.b2wdigital.lets.juvenal.producer.exceptions;

public class PayloadValidationException extends Exception{

    private final String message;

    public PayloadValidationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
