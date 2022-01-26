package com.b2wdigital.lets.juvenal.producer.validation;

import com.b2wdigital.lets.juvenal.producer.exceptions.EntityValidationException;
import com.b2wdigital.lets.juvenal.producer.shared.EntityFieldError;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface EntityValidator {
    List<EntityFieldError> validate(JsonNode message) throws EntityValidationException;

    boolean hasId(JsonNode entity);

    boolean hasType(JsonNode entity);

    boolean hasContent(JsonNode entity, String scope);
}
