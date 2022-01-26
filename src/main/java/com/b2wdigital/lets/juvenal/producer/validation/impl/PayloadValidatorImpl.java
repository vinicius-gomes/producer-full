package com.b2wdigital.lets.juvenal.producer.validation.impl;

import com.b2wdigital.lets.juvenal.producer.exceptions.DictionaryValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.EntityValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.HeaderValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.b2wdigital.lets.juvenal.producer.shared.Constants;
import com.b2wdigital.lets.juvenal.producer.shared.EntityFieldError;
import com.b2wdigital.lets.juvenal.producer.validation.DictionaryValidator;
import com.b2wdigital.lets.juvenal.producer.validation.EntityValidator;
import com.b2wdigital.lets.juvenal.producer.validation.HeaderValidator;
import com.b2wdigital.lets.juvenal.producer.validation.PayloadValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class PayloadValidatorImpl implements PayloadValidator {

    @Inject
    HeaderValidator headerValidator;
    @Inject
    EntityValidator entityValidator;
    @Inject
    DictionaryValidator dictionaryValidator;
    @Inject
    Logger log;

    ObjectMapper mapper;

    PayloadValidatorImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void validate(JsonNode message) throws HeaderValidationException, PayloadValidationException, EntityValidationException, DictionaryValidationException, JsonProcessingException {
        Map<String, List<EntityFieldError>> errors = new HashMap<>();

        List<EntityFieldError> headerErrors = headerValidator.validate(message);
        List<EntityFieldError> entityErrors = entityValidator.validate(message);
        List<EntityFieldError> dictionaryErrors = dictionaryValidator.validate(message);

        if (!headerErrors.isEmpty()) {
            errors.put("header errors", headerErrors);
        }
        if (!entityErrors.isEmpty()) {
            errors.put("entity errors", entityErrors);
        }
        if (!dictionaryErrors.isEmpty()) {
            errors.put("dictionary errors", dictionaryErrors);
        }

        if (!errors.isEmpty()) {
            log.errorf("result=error, op=validate, ids=%s, types=%s", message.get(Constants.JSON_IDS), message.get(Constants.JSON_TYPES));
            throw new PayloadValidationException(mapper.writeValueAsString(errors));
        }
    }
}
