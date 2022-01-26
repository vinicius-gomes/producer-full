package com.b2wdigital.lets.juvenal.producer.exceptions;

public class HeaderValidationException extends Exception{

    private final String message;

    public HeaderValidationException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
