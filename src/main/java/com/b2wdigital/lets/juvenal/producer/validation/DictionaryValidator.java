package com.b2wdigital.lets.juvenal.producer.validation;

import com.b2wdigital.lets.juvenal.producer.exceptions.DictionaryValidationException;
import com.b2wdigital.lets.juvenal.producer.shared.EntityFieldError;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

public interface DictionaryValidator {
    boolean isDictionaryPresent(Map.Entry<String, JsonNode> namespace);

    List<String> isAttributeValid(JsonNode scopeContent, Map.Entry<String, JsonNode> namespace);

    List<EntityFieldError> validate(JsonNode message) throws DictionaryValidationException;
}
