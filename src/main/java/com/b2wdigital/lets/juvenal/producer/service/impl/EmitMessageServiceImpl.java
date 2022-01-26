package com.b2wdigital.lets.juvenal.producer.service.impl;

import com.b2wdigital.lets.juvenal.producer.exceptions.EmitMessageException;
import com.b2wdigital.lets.juvenal.producer.service.EmitMessageService;
import com.fasterxml.jackson.databind.JsonNode;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class EmitMessageServiceImpl implements EmitMessageService {

    @Inject
    @Channel("message-stream")
    Emitter<String> emitter;

    @Override
    public void send(JsonNode message) throws EmitMessageException {
        try {
            emitter.send(message.toPrettyString())
                    .toCompletableFuture()
                    .get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new EmitMessageException(e.getMessage());
        } catch (ExecutionException e) {
            throw new EmitMessageException(e.getMessage());
        }
    }
}
