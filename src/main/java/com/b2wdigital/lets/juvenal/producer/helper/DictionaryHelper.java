package com.b2wdigital.lets.juvenal.producer.helper;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public interface DictionaryHelper {
    Pair<String, String> getNamespaces(String namespaceName);

    List<JsonNode> getDictionariesForCurrentNamespaces(Map.Entry<String, JsonNode> namespace);
}
