package com.b2wdigital.lets.juvenal.producer.service.impl;

import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.b2wdigital.lets.juvenal.producer.rest.DictionaryRestClient;
import com.b2wdigital.lets.juvenal.producer.service.DictionaryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DictionaryServiceImpl implements DictionaryService {

    @Inject
    Logger log;
    @Inject
    @RestClient
    DictionaryRestClient dictionaryRestClient;

    ObjectMapper mapper;
    DictionaryServiceImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private static final String DICT_FACTORY_NAME_ENTITY = "EntityAttributeFactory";
    private List<JsonNode> dictionaries = new ArrayList<>();

    @Override
    public List<JsonNode> getDictionaries() {
        return dictionaries;
    }

	/**
	 * Neste método, a chamada para o RestClient que recupera os dicionários foi substituida por uma leitura em arquivo estático
	 * para eliminar a necessidade de interação com outra API para propósitos de teste ou avaliação do presente código
	 * @throws PayloadValidationException
	 */
    @Override
    public void updateDictionaries() throws PayloadValidationException {
        try {
			InputStream inputStream = getClass().getResourceAsStream("/EntityAttributeFactory.json");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String dict;
            //dict = dictionaryRestClient.getDictionariesByFactory(DICT_FACTORY_NAME_ENTITY);
			dict = bufferedReader.lines().collect(Collectors.joining());
            dictionaries = mapper.readValue(dict, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.errorf("write api is not responding: %s", e);
            throw new PayloadValidationException("write api is not responding.");
        }
    }
}

