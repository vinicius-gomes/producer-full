package helper;

import com.b2wdigital.lets.juvenal.producer.helper.DictionaryHelper;
import com.b2wdigital.lets.juvenal.producer.service.DictionaryService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
class DictionaryHelperTest {

    @Inject
    DictionaryHelper dictionaryHelper;
    @InjectMock
    DictionaryService dictionaryService;

    static String namespace;
    static Pair<String, String> expectedNamespace;

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
        expectedNamespace = new ImmutablePair<>("delivery", "general");
        namespace = "delivery:general";
    }


    static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Test
    void getNamespaces() {
        Assertions.assertEquals(expectedNamespace, dictionaryHelper.getNamespaces(namespace));
    }

    @Test
    void getDictionariesForCurrentNamespaces() {
        Map.Entry<String, JsonNode> namespace = new AbstractMap.SimpleEntry<String, JsonNode>("delivery:general", content);
        Mockito.when(dictionaryService.getDictionaries()).thenReturn(dictionaryList);
        Assertions.assertEquals(dictionaryList, dictionaryHelper.getDictionariesForCurrentNamespaces(namespace));
    }

}
