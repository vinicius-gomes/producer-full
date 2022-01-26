package helper;

import com.b2wdigital.lets.juvenal.producer.helper.EntityHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@QuarkusTest
class EntityHelperTest {

    @Inject
    EntityHelper entityHelper;

    static ObjectMapper mapper;
    static String file;
    static String payload;
    static JsonNode entity;

    static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @BeforeAll
    static void init() throws Exception {
        mapper = new ObjectMapper();
        file = "src/test/resources/entity.json";
        payload = readFileAsString(file);
        entity = mapper.readTree(payload);
    }


    @Test
    void entityIdAndType() {
        Pair<String, String> expectedIdAndType = new ImmutablePair<>("352614522101", "delivery");
        Assertions.assertEquals(expectedIdAndType.toString(), entityHelper.getIdAndType(entity).toString().replace("\"", ""));
    }

    @Test
    void getContent() {
        JsonNode expectedContent = entity.get("suba");
        Assertions.assertEquals(expectedContent, entityHelper.getContent(entity, "suba"));
    }

    @Test
    void getFieldIfExists(){
        Assertions.assertEquals("352614522101", entityHelper.getFieldIfExists(entity, "id"));
    }

    @Test
    void getFieldIfExistsFails(){
        Assertions.assertEquals("id not present", entityHelper.getFieldIfExists(entity, "aspargo"));
    }
}
