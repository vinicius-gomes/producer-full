package com.b2wdigital.lets.juvenal.producer.rest;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/v1/dictionary")
@ApplicationScoped
@RegisterRestClient
public interface DictionaryRestClient {

    @GET
    @Path("/{factory_name}")
    String getDictionariesByFactory(@PathParam("factory_name") String factoryName);
}
