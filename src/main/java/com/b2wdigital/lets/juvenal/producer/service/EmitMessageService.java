package com.b2wdigital.lets.juvenal.producer.service;

import com.b2wdigital.lets.juvenal.producer.exceptions.EmitMessageException;
import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.fasterxml.jackson.databind.JsonNode;

public interface EmitMessageService {
    void send(JsonNode message) throws PayloadValidationException, EmitMessageException;
}
