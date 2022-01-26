package com.b2wdigital.lets.juvenal.producer.helper.impl;

import com.b2wdigital.lets.juvenal.producer.helper.EntityHelper;
import com.b2wdigital.lets.juvenal.producer.shared.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.tuple.Pair;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityHelperImpl implements EntityHelper {

    @Override
    public Pair<String, String> getIdAndType(JsonNode entity) {
        return Pair.of(entity.get(Constants.JSON_ID).toString(), entity.get(Constants.JSON_TYPE).toString());
    }

    @Override
    public JsonNode getContent(JsonNode entity, String scope) {
        return entity.get(scope);
    }

    @Override
    public String getFieldIfExists(JsonNode entity, String field){
        if (entity.has(field)){
            return entity.get(field).asText();
        } else {
            return "id not present";
        }
    }
}
