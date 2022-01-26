package validation;

import com.b2wdigital.lets.juvenal.producer.validation.EntityValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Paths;

@QuarkusTest
class EntityValidatorTest {

    @Inject
    EntityValidator entityValidator;

    static String payload;
    static String file = "src/test/resources/entity.json";
    static ObjectMapper mapper;
    static JsonNode entity;
    static String scope;
    static ObjectNode wrongEntity;

    @BeforeAll
    static void initAll() throws Exception {
        mapper = new ObjectMapper();
        payload = readFileAsString(file);
        entity = mapper.readTree(payload);
        scope = "suba";
        wrongEntity = mapper.createObjectNode();
    }

    static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Test
    void hasIdAndType(){
        Assertions.assertTrue(entityValidator.hasId(entity));
    }

    @Test
    void hasIdAndTypeFails(){
        wrongEntity.remove("id");
        Assertions.assertFalse(entityValidator.hasId(wrongEntity));
    }

    @Test
    void hasIdAndTypeFails2(){
        wrongEntity.set("id", null);
        Assertions.assertFalse(entityValidator.hasId(wrongEntity));
    }

    @Test
    void hasType(){
        Assertions.assertTrue(entityValidator.hasType(entity));
    }

    @Test
    void hasTypeFails(){
        wrongEntity.remove("type");
        Assertions.assertFalse(entityValidator.hasType(wrongEntity));
    }

    @Test
    void hasTypeFails2(){
        wrongEntity.set("type", null);
        Assertions.assertFalse(entityValidator.hasType(wrongEntity));
    }

    @Test
    void hasContent(){
        Assertions.assertTrue(entityValidator.hasContent(entity, scope));
    }

    @Test
    void hasContentFails(){
        wrongEntity.set(scope, null);
        Assertions.assertFalse(entityValidator.hasContent(wrongEntity, scope));
    }
}
