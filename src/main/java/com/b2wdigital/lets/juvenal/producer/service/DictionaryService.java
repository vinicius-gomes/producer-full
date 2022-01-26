package com.b2wdigital.lets.juvenal.producer.service;

import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface DictionaryService {

    List<JsonNode> getDictionaries();

    void updateDictionaries() throws PayloadValidationException;
}
