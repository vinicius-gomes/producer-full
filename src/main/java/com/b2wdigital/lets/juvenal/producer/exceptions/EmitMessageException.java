package com.b2wdigital.lets.juvenal.producer.exceptions;

public class EmitMessageException extends Exception{

    private final String message;

    public EmitMessageException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
