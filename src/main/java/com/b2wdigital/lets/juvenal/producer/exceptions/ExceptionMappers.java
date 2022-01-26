package com.b2wdigital.lets.juvenal.producer.exceptions;

import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ExceptionMappers {

    @ServerExceptionMapper
    public Response mapHeaderValidationException(HeaderValidationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(exception.getMessage()).build();
    }

    @ServerExceptionMapper
    public Response mapEntityValidationException(EntityValidationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(exception.getMessage()).build();
    }

    @ServerExceptionMapper
    public Response mapDictionaryValidationException(DictionaryValidationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(exception.getMessage()).build();
    }

    @ServerExceptionMapper
    public Response mapPayloadValidationException(PayloadValidationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(exception.getMessage()).build();
    }

    @ServerExceptionMapper
    public Response mapEmitMessageException(EmitMessageException exception) {
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(exception.getMessage()).build();
    }
}
