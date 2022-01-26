package com.b2wdigital.lets.juvenal.producer.helper.impl;

import com.b2wdigital.lets.juvenal.producer.helper.MessageHelper;
import com.b2wdigital.lets.juvenal.producer.shared.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class MessageHelperImpl implements MessageHelper {

    @Inject
    Logger log;

    ObjectMapper mapper;

    MessageHelperImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public JsonNode toJson(String message) {
        try {
            if (!message.isBlank()) {
                return mapper.readTree(message);
            }
        } catch (final Exception e) {
            log.errorf("result=error, op=toJson, message=json parsing error, item=%s", message);
        }
        throw new WebApplicationException(String.format("json parsing error: %s", message), Response.status(400).type(MediaType.APPLICATION_JSON_TYPE).entity("json parsing error, invalid json format.").build());
    }

    @Override
    public Pair<String, String> getIdsAndTypes(JsonNode message) {
        return Pair.of(message.get(Constants.JSON_IDS).toString(), message.get(Constants.JSON_TYPES).toString());
    }

    @Override
    public String getScope(JsonNode message) {
        return String.valueOf(message.get(Constants.JSON_SCOPE)).toLowerCase().replace("\"", "");
    }

    @Override
    public List<JsonNode> groupEntities(JsonNode message) {
        List<JsonNode> entities = new ArrayList<>();
        Stream.of("orders", "deliveries", "deliverylines", "deliverypayments", "instances").forEach(typeArray -> {
            if (message.has(typeArray)) {
                message.get(typeArray).elements().forEachRemaining(entities::add);
            }
        });
        return entities;
    }
}

