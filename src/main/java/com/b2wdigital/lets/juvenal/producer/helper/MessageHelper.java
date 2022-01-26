package com.b2wdigital.lets.juvenal.producer.helper;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface MessageHelper {
    JsonNode toJson(String message);

    Pair<String, String> getIdsAndTypes(JsonNode message);

    String getScope(JsonNode message);

    List<JsonNode> groupEntities(JsonNode message);
}
