package validation;

import com.b2wdigital.lets.juvenal.producer.service.DictionaryService;
import com.b2wdigital.lets.juvenal.producer.validation.DictionaryValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@QuarkusTest
class DictionaryValidatorTest {
    @Inject
    DictionaryValidator dictionaryValidator;
    @InjectMock
    DictionaryService dictionaryService;

    static String payload;
    static String file = "src/test/resources/entity.json";
    static String file2 = "src/test/resources/dictionary.json";
    static ObjectMapper mapper;
    static JsonNode entity;
    static String scope;
    static JsonNode content;
    static List<JsonNode> dictionaryList = new ArrayList<>();
    static String dictionaryPayload;
    static JsonNode dictionary;
    static ObjectNode wrongContent;

    @BeforeAll
    static void initAll() throws Exception {
        mapper = new ObjectMapper();
        payload = readFileAsString(file);
        dictionaryPayload = readFileAsString(file2);
        entity = mapper.readTree(payload);
        dictionary = mapper.readTree(dictionaryPayload);
        scope = "suba";
        dictionaryList.add(dictionary);
        content = entity.get("suba");
        wrongContent = mapper.createObjectNode();
        wrongContent.put("delivery:general", "doisPatinhosNaLagoa");
    }

    static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Test
    void isDictionaryFound(){
        Map.Entry<String, JsonNode> namespace = new AbstractMap.SimpleEntry<String, JsonNode>("delivery:general", content);
        Mockito.when(dictionaryService.getDictionaries()).thenReturn(dictionaryList);
        Assertions.assertTrue(dictionaryValidator.isDictionaryPresent(namespace));
    }

    @Test
    void isDictionaryFoundFails(){
        Map.Entry<String, JsonNode> namespace = new AbstractMap.SimpleEntry<String, JsonNode>("delivery:bananinha", content);
        Mockito.when(dictionaryService.getDictionaries()).thenReturn(dictionaryList);
        Assertions.assertFalse(dictionaryValidator.isDictionaryPresent(namespace));
    }

    @Test
    void isAttributeValid(){
        Map.Entry<String, JsonNode> namespace = new AbstractMap.SimpleEntry<String, JsonNode>("delivery:general", content);
        Mockito.when(dictionaryService.getDictionaries()).thenReturn(dictionaryList);
        Assertions.assertTrue(dictionaryValidator.isDictionaryPresent(namespace));
    }

}
