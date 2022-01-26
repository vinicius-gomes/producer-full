package com.b2wdigital.lets.juvenal.producer.validation;

import com.b2wdigital.lets.juvenal.producer.exceptions.HeaderValidationException;
import com.b2wdigital.lets.juvenal.producer.shared.EntityFieldError;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface HeaderValidator {
    boolean containsAllEntities(List<JsonNode> entities);

    boolean hasScope(JsonNode message);

    boolean hasIds(JsonNode message);

    boolean hasTypes(JsonNode message);

    boolean hasEventTime(JsonNode message);

    boolean isEventTimeValid(JsonNode message);

    List<EntityFieldError> validate(JsonNode message) throws HeaderValidationException;
}
