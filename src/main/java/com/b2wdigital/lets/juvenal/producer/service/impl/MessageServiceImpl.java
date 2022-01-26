package com.b2wdigital.lets.juvenal.producer.service.impl;

import com.b2wdigital.lets.juvenal.producer.exceptions.DictionaryValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.EmitMessageException;
import com.b2wdigital.lets.juvenal.producer.exceptions.EntityValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.HeaderValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.b2wdigital.lets.juvenal.producer.service.EmitMessageService;
import com.b2wdigital.lets.juvenal.producer.service.MessageService;
import com.b2wdigital.lets.juvenal.producer.validation.PayloadValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MessageServiceImpl implements MessageService {

    @Inject
    EmitMessageService emitService;
    @Inject
    PayloadValidator payloadValidator;

    @Override
    public void send(JsonNode message) throws PayloadValidationException, HeaderValidationException, EntityValidationException, DictionaryValidationException, JsonProcessingException, EmitMessageException {
        payloadValidator.validate(message);
        emitService.send(message);
    }

}
