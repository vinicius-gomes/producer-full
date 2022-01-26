package com.b2wdigital.lets.juvenal.producer.helper;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.tuple.Pair;

public interface EntityHelper {
    Pair<String, String> getIdAndType(JsonNode entity);

    JsonNode getContent(JsonNode entity, String scope);

    String getFieldIfExists(JsonNode entity, String field);
}
