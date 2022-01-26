package validation;

import com.b2wdigital.lets.juvenal.producer.helper.MessageHelper;
import com.b2wdigital.lets.juvenal.producer.validation.HeaderValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@QuarkusTest
class HeaderValidatorTest {

    @Inject
    HeaderValidator headerValidator;
    @Inject
    MessageHelper messageHelper;

    static String payload;
    static String file = "src/test/resources/payload.json";
    static ObjectMapper mapper;
    static JsonNode emptyEntity;
    JsonNode message;
    List<JsonNode> entities;

    @BeforeAll
    static void initAll() throws Exception {
        mapper = new ObjectMapper();
        payload = readFileAsString(file);
        emptyEntity = mapper.readTree("{}");
    }

    @BeforeEach
    void init() throws Exception {
        message = mapper.readTree(payload);
        entities = messageHelper.groupEntities(message);
    }

    static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Test
    void hasIds() {
        Assertions.assertTrue(headerValidator.hasIds(message));
    }

    @Test
    void hasIdsFails() {
        JsonNode messageWithoutIds = ((ObjectNode) message).remove("ids");
        Assertions.assertFalse(headerValidator.hasIds(messageWithoutIds));
    }

    @Test
    void hasTypes() {
        Assertions.assertTrue(headerValidator.hasTypes(message));
    }

    @Test
    void hasTypesFails() {
        JsonNode messageWithoutIds = ((ObjectNode) message).remove("types");
        Assertions.assertFalse(headerValidator.hasTypes(messageWithoutIds));
    }

    @Test
    void hasScope() {
        Assertions.assertTrue(headerValidator.hasScope(message));
    }

    @Test
    void hasScopeFails() {
        JsonNode messageWithoutIds = ((ObjectNode) message).remove("scope");
        Assertions.assertFalse(headerValidator.hasScope(messageWithoutIds));
    }

    @Test
    void containsAllEntities() {
        Assertions.assertTrue(headerValidator.containsAllEntities(entities));
    }

    @Test
    void containsAllEntitiesFails() {
        entities.add(emptyEntity);
        Assertions.assertFalse(headerValidator.containsAllEntities(entities));
    }

    @Test
    void hasEventTime() {
        Assertions.assertTrue(headerValidator.hasEventTime(message));
    }

    @Test
    void hasEventTimeFails() {
        JsonNode messageWithoutEventTime = ((ObjectNode) message).remove("event_time");
        Assertions.assertFalse(headerValidator.hasEventTime(messageWithoutEventTime));
    }

    @Test
    void isEventTimeValid() {
        Assertions.assertTrue(headerValidator.isEventTimeValid(message));
    }

    @Test
    void isEventTimeValidFails() {
        ((ObjectNode) message).remove("event_time");

        String futureEventTime = Instant.now().plus(25, ChronoUnit.HOURS).toString();
        JsonNode messageWithFutureEventTime = ((ObjectNode) message).put("event_time", futureEventTime);
        Assertions.assertFalse(headerValidator.isEventTimeValid(messageWithFutureEventTime));
    }
}
