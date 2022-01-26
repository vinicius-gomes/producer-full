package service;

import com.b2wdigital.lets.juvenal.producer.exceptions.DictionaryValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.EmitMessageException;
import com.b2wdigital.lets.juvenal.producer.exceptions.EntityValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.HeaderValidationException;
import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.b2wdigital.lets.juvenal.producer.validation.HeaderValidator;
import com.b2wdigital.lets.juvenal.producer.validation.DictionaryValidator;
import com.b2wdigital.lets.juvenal.producer.service.EmitMessageService;
import com.b2wdigital.lets.juvenal.producer.validation.EntityValidator;
import com.b2wdigital.lets.juvenal.producer.service.MessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.Mockito.times;

@QuarkusTest
class MessageServiceTest {

    @InjectMock
    EntityValidator entityValidator;
    @InjectMock
    DictionaryValidator dictionaryValidatorService;
    @InjectMock
    EmitMessageService emitService;
    @InjectMock
    HeaderValidator headerValidator;
    @Inject
    MessageService messageService;

    JsonNode message;
    String payload;
    String file = "src/test/resources/payload.json";
    ObjectMapper mapper;

    @BeforeEach
    void init() throws Exception {
        mapper = new ObjectMapper();
        payload = readFileAsString(file);
        message = mapper.readTree(payload);
    }

    String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Test
    void send() throws HeaderValidationException, EntityValidationException, DictionaryValidationException, PayloadValidationException, EmitMessageException {
        Assertions.assertDoesNotThrow(() -> messageService.send(message));
        Mockito.verify(headerValidator, times(1)).validate(message);
        Mockito.verify(entityValidator, times(1)).validate(message);
        Mockito.verify(dictionaryValidatorService, times(1)).validate(message);
        Mockito.verify(emitService, times(1)).send(message);
    }
}
