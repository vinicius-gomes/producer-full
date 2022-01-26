package com.b2wdigital.lets.juvenal.producer.resource.impl;

import com.b2wdigital.lets.juvenal.producer.exceptions.DictionaryValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.EmitMessageException;
import com.b2wdigital.lets.juvenal.producer.exceptions.EntityValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.HeaderValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.b2wdigital.lets.juvenal.producer.helper.MessageHelper;
import com.b2wdigital.lets.juvenal.producer.resource.MessageResource;
import com.b2wdigital.lets.juvenal.producer.service.MessageService;
import com.b2wdigital.lets.juvenal.producer.shared.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class MessageResourceImpl implements MessageResource {

    @Inject
    MessageService messageService;
    @Inject
    MessageHelper messageHelper;
    @Inject
    Logger log;

    @Override
    public Response send(String payload) throws HeaderValidationException, PayloadValidationException, EntityValidationException, DictionaryValidationException, JsonProcessingException, EmitMessageException {
        long startTime = System.currentTimeMillis();
        JsonNode message = messageHelper.toJson(payload);

        messageService.send(message);
        log.infof("result=success, status=ok, op=send, ids=%s, types=%s, time=%sms", message.get(Constants.JSON_IDS), message.get(Constants.JSON_TYPES), System.currentTimeMillis() - startTime);
        return Response.ok().build();
    }
}