package helper;

import com.b2wdigital.lets.juvenal.producer.helper.MessageHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@QuarkusTest
class MessageHelperTest {

    @Inject
    MessageHelper messageHelper;

    static ObjectMapper mapper;
    static String file;
    static String payload;
    static JsonNode message;

    static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @BeforeAll
    static void init() throws Exception {
        mapper = new ObjectMapper();
        file = "src/test/resources/payload.json";
        payload = readFileAsString(file);
        message = mapper.readTree(payload);
    }

    @Test
    void toJson() {
        Assertions.assertEquals(message, messageHelper.toJson(payload));
    }

    @Test
    void toJsonFails() {
        String wrongPayload = "{[order,}";
        Assertions.assertThrows(WebApplicationException.class, () -> messageHelper.toJson(wrongPayload));
    }

    @Test
    void getIdsAndTypes() {
        String ids = message.get("ids").toString();
        String types = message.get("types").toString();

        Pair<String, String> idsAndTypes = Pair.of(ids, types);
        Assertions.assertEquals(idsAndTypes, messageHelper.getIdsAndTypes(message));
    }

    @Test
    void groupEntitiesInJsonArray() {
        List<JsonNode> expectedEntities = new ArrayList<>();

        message.get("orders").elements().forEachRemaining(expectedEntities::add);
        message.get("deliveries").elements().forEachRemaining(expectedEntities::add);
        message.get("deliverylines").elements().forEachRemaining(expectedEntities::add);
        message.get("deliverypayments").elements().forEachRemaining(expectedEntities::add);

        Assertions.assertEquals(expectedEntities, messageHelper.groupEntities(message));
    }
}
