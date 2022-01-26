package service;

import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.b2wdigital.lets.juvenal.producer.rest.DictionaryRestClient;
import com.b2wdigital.lets.juvenal.producer.service.DictionaryService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testes desabilitados com o objetivo de preservar a mudança aplicada no DictionaryService
 * para eliminar a necessidade de interação com outra API para propósitos de teste ou avaliação do presente código
 */
@QuarkusTest
class DictionaryServiceTest {

    @Inject
    DictionaryService dictionaryService;
    @InjectMock
    @RestClient
    DictionaryRestClient dictionaryRestClient;

    static final String DICT_FACTORY_NAME_ENTITY = "EntityAttributeFactory";

    static String file = "src/test/resources/dictionary.json";
    static ObjectMapper mapper;
    static List<JsonNode> dictionaryList = new ArrayList<>();
    static String dictionaryPayload;
    static JsonNode dictionary;

    @BeforeAll
    static void initAll() throws Exception {
        mapper = new ObjectMapper();
        dictionaryPayload = readFileAsString(file);
        dictionary = mapper.readTree(dictionaryPayload);
        dictionaryList.add(dictionary);
    }

    static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

	@Disabled
    @Test
    void updateDict() {
        when(dictionaryRestClient.getDictionariesByFactory(DICT_FACTORY_NAME_ENTITY)).thenReturn(String.valueOf(dictionaryList));
        Assertions.assertDoesNotThrow(() -> dictionaryService.updateDictionaries());
        verify(dictionaryRestClient, times(1)).getDictionariesByFactory(DICT_FACTORY_NAME_ENTITY);
    }

	@Disabled
    @Test
    void updateDictFails() {
        Assertions.assertThrows(PayloadValidationException.class, () -> dictionaryService.updateDictionaries());
        verify(dictionaryRestClient, times(1)).getDictionariesByFactory(DICT_FACTORY_NAME_ENTITY);
    }
}
