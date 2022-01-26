package com.b2wdigital.lets.juvenal.producer.resource;

import com.b2wdigital.lets.juvenal.producer.exceptions.DictionaryValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.EmitMessageException;
import com.b2wdigital.lets.juvenal.producer.exceptions.EntityValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.HeaderValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/message")
public interface MessageResource {

    @POST
    @Tag(name = "Enviar payload")
    @Operation(summary = "Valida pedido, posta em um tópico Kafka.")
    Response send(String payload) throws HeaderValidationException, PayloadValidationException, EntityValidationException, DictionaryValidationException, JsonProcessingException, EmitMessageException;
}
