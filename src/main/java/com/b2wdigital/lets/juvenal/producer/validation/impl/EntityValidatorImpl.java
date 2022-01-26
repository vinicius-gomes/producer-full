package com.b2wdigital.lets.juvenal.producer.validation.impl;

import com.b2wdigital.lets.juvenal.producer.exceptions.EntityValidationException;
import com.b2wdigital.lets.juvenal.producer.helper.EntityHelper;
import com.b2wdigital.lets.juvenal.producer.helper.MessageHelper;
import com.b2wdigital.lets.juvenal.producer.shared.EntityFieldError;
import com.b2wdigital.lets.juvenal.producer.validation.EntityValidator;
import com.b2wdigital.lets.juvenal.producer.shared.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EntityValidatorImpl implements EntityValidator {
    @Inject
    Logger log;
    @Inject
    MessageHelper messageHelper;
    @Inject
    EntityHelper entityHelper;

    @Override
    public List<EntityFieldError> validate(JsonNode message) throws EntityValidationException {
        List<EntityFieldError> errorList = new ArrayList<>();
        List<String> entitiesWithoutContent = new ArrayList<>();
        List<String> entitiesWithoutType = new ArrayList<>();

        List<JsonNode> entities = messageHelper.groupEntities(message);
        String scope = messageHelper.getScope(message);

        entities.forEach(entity -> {
            if (!hasId(entity)) {
                errorList.add(new EntityFieldError(null, "entity id should not be empty or null"));
            }
        });

        entities.forEach(entity -> {
            if (!hasType(entity)) {
                entitiesWithoutType.add(entityHelper.getFieldIfExists(entity, Constants.JSON_ID));
            }
        });

        entities.forEach(entity -> {
            if (!hasContent(entity, scope)) {
                entitiesWithoutContent.add(entityHelper.getFieldIfExists(entity, Constants.JSON_ID));
            }
        });

        if (!entitiesWithoutType.isEmpty()) {
            errorList.add(new EntityFieldError(entitiesWithoutType, "entity type should not be empty or null"));
        }

        if (!entitiesWithoutContent.isEmpty()) {
            log.errorf("result=error, op=hasContent, entity content should not be empty or null, itens=%s", entitiesWithoutContent);
            throw new EntityValidationException(String.format("entity content should not be empty or null, itens: %s", entitiesWithoutContent));
        }

        return errorList;
    }

    @Override
    public boolean hasId(JsonNode entity) {
        if (entity.has(Constants.JSON_ID)) {
            if (!entity.get(Constants.JSON_ID).isNull() && !entity.get(Constants.JSON_ID).toString().replace("\"", "").isBlank()) {
                return true;
            } else {
                log.errorf("result=error, op=hasId, entity id should not be empty or null");
                return false;
            }
        } else {
            log.errorf("result=error, op=hasId, entity must have id");
            return false;
        }
    }

    @Override
    public boolean hasType(JsonNode entity) {
        if (entity.has(Constants.JSON_TYPE)) {
            if (!entity.get(Constants.JSON_TYPE).isNull() && !entity.get(Constants.JSON_TYPE).toString().replace("\"", "").isBlank()) {
                return true;
            } else {
                log.errorf("result=error, op=hasType, entity type should not be empty or null, item=%s", entityHelper.getFieldIfExists(entity, Constants.JSON_ID));
                return false;
            }
        } else {
            log.errorf("result=error, op=hasType, entity must have type, item=%s", entityHelper.getFieldIfExists(entity, Constants.JSON_ID));
            return false;
        }
    }

    @Override
    public boolean hasContent(JsonNode entity, String scope) {
        return entity.has(scope) && !entity.get(scope).isNull() && !entity.get(scope).toString().replace("\"", "").isBlank() && !entity.get(scope).isEmpty();
    }
}
