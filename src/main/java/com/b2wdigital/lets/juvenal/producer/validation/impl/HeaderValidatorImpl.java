package com.b2wdigital.lets.juvenal.producer.validation.impl;

import com.b2wdigital.lets.juvenal.producer.exceptions.HeaderValidationException;
import com.b2wdigital.lets.juvenal.producer.helper.MessageHelper;
import com.b2wdigital.lets.juvenal.producer.shared.EntityFieldError;
import com.b2wdigital.lets.juvenal.producer.validation.HeaderValidator;
import com.b2wdigital.lets.juvenal.producer.shared.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class HeaderValidatorImpl implements HeaderValidator {

    @Inject
    Logger log;
    @Inject
    MessageHelper messageHelper;

    @Override
    public List<EntityFieldError> validate(JsonNode message) throws HeaderValidationException {
        List<JsonNode> entities = messageHelper.groupEntities(message);
        List<EntityFieldError> errorList = new ArrayList<>();

        if (!hasScope(message)) {
            errorList.add(new EntityFieldError(null, "header must have scope"));
        }
        if (!hasIds(message)) {
            errorList.add(new EntityFieldError(null, "header must have ids"));
        }
        if (!hasTypes(message)) {
            errorList.add(new EntityFieldError(null, "header must have types"));
        }
        if (!hasEventTime(message)) {
            errorList.add(new EntityFieldError(null, "header must have event_time"));
        } else if (!isEventTimeValid(message)) {
            errorList.add(new EntityFieldError(null, "event_time is a future date"));
        }

        if (!containsAllEntities(entities)) {
            log.errorf("result=error, op=containsAllEntities, message=no entity should be empty or null");
            throw new HeaderValidationException("no entity should be empty or null");
        }

        return errorList;
    }

    @Override
    public boolean isEventTimeValid(JsonNode message) {
        var eventTimeString = message.get(Constants.JSON_EVENT_TIME).toString().replace("\"", "");
        var eventTime = Instant.parse(eventTimeString);
        Instant dateLimit = Instant.now().plus(24, ChronoUnit.HOURS);

        if (hasEventTime(message) && eventTime.isAfter(dateLimit)) {
            log.errorf("result=error, op=isEventTimeValid, eventTime=%s, event_time is a future date", eventTime);
            return false;
        }
        return true;
    }

    @Override
    public boolean hasIds(JsonNode message) {
        if (message.has(Constants.JSON_IDS)) {
            return true;
        } else {
            log.errorf("result=error, op=hasIds, message=header must have ids");
            return false;
        }
    }

    @Override
    public boolean hasTypes(JsonNode message) {
        if (message.has(Constants.JSON_TYPES)) {
            return true;
        } else {
            log.errorf("result=error, op=hasTypes, message=header must have types");
            return false;
        }
    }

    @Override
    public boolean hasEventTime(JsonNode message) {
        if (message.has(Constants.JSON_EVENT_TIME)) {
            return true;
        } else {
            log.errorf("result=error, op=hasEventTime, message=header must have event_time");
            return false;
        }
    }

    @Override
    public boolean hasScope(JsonNode message) {
        if (message.has(Constants.JSON_SCOPE) && !message.get(Constants.JSON_SCOPE).isNull() && !message.get(Constants.JSON_SCOPE).toString().replace("\"", "").isBlank()) {
            return true;
        } else {
            log.errorf("result=error, op=hasScope, message=header must have scope");
            return false;
        }
    }

    @Override
    public boolean containsAllEntities(List<JsonNode> entities) {
        return entities.stream().noneMatch(entity -> (entity.isEmpty() || entity.isNull()));
    }
}