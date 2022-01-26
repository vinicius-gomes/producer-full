package com.b2wdigital.lets.juvenal.producer.validation;

import com.b2wdigital.lets.juvenal.producer.exceptions.DictionaryValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.EntityValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.HeaderValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface PayloadValidator {
    void validate(JsonNode message) throws HeaderValidationException, PayloadValidationException, EntityValidationException, DictionaryValidationException, JsonProcessingException;
}
